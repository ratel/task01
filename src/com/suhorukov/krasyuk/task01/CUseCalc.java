package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.FieldCmd;
import com.suhorukov.krasyuk.cmd.ICmd;
import com.suhorukov.krasyuk.cmd.fieldCmdKind;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;

import static com.suhorukov.krasyuk.cmd.fieldCmdKind.*;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 01.07.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class CUseCalc {
    private Scanner scn;                                                                    // Считывание команд пользователя.
    private WorkType workMode;                                                              // Режим работы (1- считываем из файла, 2- с консоли).
    private String  lastCmdLine= "";                                                        // Последняя считанная команда.
    final String CMD_EXIT= "exit";                                                          // Команда прерывания ввода данных.
    public enum WorkType {WHILEREAD, NOTEXIT, DONTWORK}

    public CUseCalc (InputStream inStream, WorkType workMode) {
        scn= new Scanner(inStream);
        this.workMode= workMode;
    }

    public CUseCalc (File file, WorkType workMode) {
        try {
            scn= new Scanner(file);
            this.workMode= workMode;
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл \"" + file.getName() + "\"");
            this.workMode= WorkType.DONTWORK;
        }
    }

    public String getNextCmd() {
        lastCmdLine= scn.nextLine();
        return lastCmdLine;
    }

    public boolean isWokrs() {
        switch (workMode) {
            case WHILEREAD: return scn.hasNext();
            case NOTEXIT: return (!lastCmdLine.equals(CMD_EXIT));
            //defaul: return false;
        }
        return false;
    }

    private Hashtable<fieldCmdKind, Object> InitValuesFieldForCmd() {
        Hashtable<fieldCmdKind, Object> fieldsValue= new Hashtable<fieldCmdKind, Object>();     // Набор пар видов полей и значений для них.

        fieldsValue.put(STACK, (new Stack<Double>()));                                          // Стек значений
        fieldsValue.put(CONTEXT, (new Hashtable<String, Double>()));                            // Словарь замен.

        return fieldsValue;
    }

    public int buildCalc(String resName, CStackCalc calc) {
        Properties cmdList= new Properties();

        try {
            InputStreamReader readerCmdList= new InputStreamReader(CMain.class.getResourceAsStream(resName));
            cmdList.load(readerCmdList);
            Hashtable<fieldCmdKind, Object> fieldsValue= InitValuesFieldForCmd();               // Набор пар видов полей и значений для них.

            for (Map.Entry<Object, Object> icmdList: cmdList.entrySet()) {
                addCmdInCalc(calc, icmdList.getKey().toString(), icmdList.getValue().toString(), fieldsValue);
            }

            return 0;
        }
        catch (Exception e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.err.println("Не удалось загрузить ресурс с перечнем команд калькулятора!");
            return -1;
        }
    }

    private void addCmdInCalc(CStackCalc calc, String cmdName, String className, Hashtable<fieldCmdKind, Object> fieldsValue) {
        ICmd cmd;
        Object o= null;
        Class cls= null;

        try {
            cls = Class.forName(className);
            o= cls.newInstance();
            if (o instanceof ICmd) {
                cmd= (ICmd) o;
                cmd.setCmdText(cmdName.toUpperCase());
                InitFieldCmd(cmd, fieldsValue);
                calc.addCmdCalc(cmd);
                System.out.println("Добавили команду в калькулятор (" + cmd.getCmdText() + ")!");
            }
        }
        catch (Exception e) {
            System.err.println("При создании команды для калькулятора использовано неизвестное имя файла (" + className + ")!");
        }
    }

    private void InitFieldCmd(ICmd cmd, Hashtable<fieldCmdKind, Object> fieldsValue) {
        Class c= cmd.getClass();
        Field f[];

        while (c != null) {
            f= c.getDeclaredFields();

            for (int i= 0; i < f.length; i++) {
                setFieldValue(f[i], cmd, fieldsValue);
            }

            c= c.getSuperclass();
        }
    }

    private void setFieldValue(Field f, Object obj, Hashtable<fieldCmdKind, Object> fieldsValue) {
        FieldCmd annotationField;
        Object value;

        f.setAccessible(true);
        annotationField= f.getAnnotation(FieldCmd.class);

        if (annotationField != null) {
            value= fieldsValue.get(annotationField.fieldType());

            if (value != null) {
                try {
                    f.set(obj, value);
                } catch (IllegalAccessException e) {
//                        System.err.println("Ошибка при устрановлении полю \"" + f.getName() + "\" класса \"" + c.getName() + "\" ссылки на стек калькулятора.");
                    System.err.println("Ошибка при устрановлении полю \"" + f.getName() + "\" ссылки по установленному типу \"" + annotationField.fieldType() + "\".");
                }
            }
        }
    }
}
