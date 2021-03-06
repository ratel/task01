package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.FieldCmd;
import com.suhorukov.krasyuk.cmd.FieldCmdKind;
import com.suhorukov.krasyuk.cmd.ICmd;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.Void;
import java.util.*;

import static com.suhorukov.krasyuk.cmd.FieldCmdKind.*;


/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 01.07.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class CalcManager {
    private Scanner scn;                                                                // Считывание команд пользователя.
    private WorkType workMode;                                                          // Режим работы (1- считываем из файла, 2- с консоли).
    private String  lastCmdLine= "";                                                    // Последняя считанная команда.
    final private String CMD_EXIT= "exit";                                              // Команда прерывания ввода данных.
    private Stack<Double> dataStack= new Stack<>();                                     // Стек значений
    private Hashtable<String, Double> dictionaryDefine= new Hashtable<>();              // Словарь замен.
    private ProxyMode proxyMode= ProxyMode.PROXYIN;                                     // Режим с заменой команд на их прокси.
    private static final Logger log = Logger.getLogger(CalcManager.class);              // Логгер.

    public enum WorkType {WHILEREAD, NOTEXIT, DONTWORK}
    public enum ProxyMode {PROXYIN, PROXYOUT}

    public CalcManager(Scanner scn, WorkType workMode, ProxyMode proxyMode) {
        this.scn= scn;
        this.workMode= workMode;
        this.proxyMode= proxyMode;
    }

    public String getNextCmd() {
        if (scn != null)
            lastCmdLine= scn.nextLine();

        return lastCmdLine;
    }

    public boolean isWorks() {
        switch (workMode) {
            case WHILEREAD: return scn.hasNext();
            case NOTEXIT: return (!lastCmdLine.equals(CMD_EXIT));
            //defaul: return false;
        }

        return false;
    }


    private void outStackInLog() {
        if (log.isDebugEnabled()) {
            StringBuilder outStr = new StringBuilder();

            if (dataStack != null)
                for (Double iStack: dataStack) {
                    outStr.append(iStack);
                    outStr.append(";  ");
                }
            else
                outStr.append("Stack is null.");

            if (outStr.length() == 0)
                outStr.append("Stack is empty.");

            log.debug(String.valueOf(outStr));
        }
    }

    private void outContextInLog() {
        if (log.isDebugEnabled()) {
            StringBuilder outStr = new StringBuilder();

            if (dictionaryDefine != null)
                for (Map.Entry<String, Double> iDictionary: dictionaryDefine.entrySet()) {
                    //outStr.append(iDictionary.getKey() + " = " + iDictionary.getValue() + ";  ");
                    outStr.append(iDictionary.getKey());
                    outStr.append(" = ");
                    outStr.append(iDictionary.getValue());
                    outStr.append(";  ");
                }
            else
                outStr.append("Context is null.");

            if (outStr.length() == 0)
                outStr.append("Context is empty.");

            log.debug(String.valueOf(outStr));
        }
    }

    private Hashtable<FieldCmdKind, Object> InitValuesFieldForCmd() {
        Hashtable<FieldCmdKind, Object> fieldsValue= new Hashtable<>();                     // Набор пар видов полей и значений для них.

        fieldsValue.put(STACK, dataStack);                                                  // Стек значений
        fieldsValue.put(CONTEXT, dictionaryDefine);                                         // Словарь замен.

        return fieldsValue;
    }

    public void buildCalc(InputStreamReader readerCmdList, CStackCalc calc) throws IOException {
        Properties cmdMap= new Properties();                                               // Перечень комманд калькулятора для создания.

        cmdMap.load(readerCmdList);
        Hashtable<FieldCmdKind, Object> fieldsValue= InitValuesFieldForCmd();          // Перечень пар (тип поля, значение для него) (инициализация полей команды).

        for (Map.Entry<Object, Object> icmdList: cmdMap.entrySet()) {
            addCmdInCalc(calc, icmdList.getKey().toString(), icmdList.getValue().toString(), fieldsValue);
        }
    }

    private void addCmdInCalc(CStackCalc calc, String cmdName, String className, Hashtable<FieldCmdKind, Object> fieldsValue) {
        Class cls;                                                                          // Полученный по тектовому имени объект класса Class.
        Object o;                                                                           // Создаваемый по имени класса объект команды.
        ICmd cmd;                                                                           // Объект команды, приведенный к базовому классу команды.

        try {
            cls = Class.forName(className);
            o= cls.newInstance();

            if (o instanceof ICmd) {
                cmd= (ICmd) o;
                cmd.setCmdText(cmdName.toUpperCase());
                InitFieldCmd(cmd, fieldsValue);

                if (proxyMode == ProxyMode.PROXYIN)
                    cmd= cmdProxy(cmd);

                calc.addCmdCalc(cmd);
                log.info("Добавили команду в калькулятор (" + cmd.getCmdText() + ")!");
            }
        }
        catch (Exception e) {
            log.warn("При создании команды для калькулятора использовано неизвестное имя класса (" + className + ")!");
        }
    }

    private void InitFieldCmd(ICmd cmd, Hashtable<FieldCmdKind, Object> fieldsValue) {
        Class c= cmd.getClass();                                                            // Класс рассматриваемой команды.
        Field f[];                                                                          // Набор полей рассматриваемого класса.

        while (c != null) {
            f= c.getDeclaredFields();

            for (Field iF : f) {
                setFieldValue(iF, cmd, fieldsValue);
            }

            c= c.getSuperclass();
        }
    }

    private void setFieldValue(Field f, Object obj, Hashtable<FieldCmdKind, Object> fieldsValue) {
        FieldCmd annotationField;                                                           // Аннотация описывающая тип поля.
        Object value;                                                                       // Значение устанавливаемое полю (в зависимости от типа поля).

        f.setAccessible(true);
        annotationField= f.getAnnotation(FieldCmd.class);

        if (annotationField != null) {
            value= fieldsValue.get(annotationField.fieldType());

            if (value != null) {
                try {
                    f.set(obj, value);
                } catch (IllegalAccessException e) {
                    log.error("Ошибка при устрановлении полю \"" + f.getName() + "\" ссылки по установленному типу \"" + annotationField.fieldType() + "\".");
                }
            }
        }
    }

    private ICmd cmdProxy(ICmd cmd) {
        ICmd proxyCmd;                                                                      // Созданная прокси команды.

        proxyCmd= (ICmd) Proxy.newProxyInstance(ICmd.class.getClassLoader(), new Class<?>[]{ICmd.class},
                new CmdProxy(cmd));

        return proxyCmd;
    }

    public class CmdProxy implements InvocationHandler {
        ICmd cmd;                                                                           // Реальная команда, "обернутая" в прокси.

        public CmdProxy(ICmd cmd) {
            this.cmd= cmd;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getCmdText".equals(method.getName())) {
                return cmd.getCmdText();
            }
            if ("setCmdText".equals(method.getName())) {
                if (args.length > 0)
                    cmd.setCmdText((String) args[0]);
                    return (Void.TYPE);
            }
            if ("execute".equals(method.getName())) {
                if (args.length > 0) {
                    if (log.isDebugEnabled()) {
                        log.debug("Stack before:");
                        outStackInLog();
                        log.debug("Context:");
                        outContextInLog();

                        StringBuilder outStr = new StringBuilder();

                        log.debug("Arguments:");

                        for (Object iArgs: args) {
                            outStr.append(iArgs);
                            outStr.append(";  ");
                        }

                        log.debug(outStr);
                    }

                    cmd.execute((String) args[0]);                                 // Результат реально отработавшей функции.

                    if (log.isDebugEnabled()) {
                        log.debug("Stack After:");
                        outStackInLog();
                    }

                }
                return Void.TYPE;
            }

            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
