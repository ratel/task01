package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.ICmd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**            yt
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 01.07.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class CUseCalc {
    private Scanner scn;                                                                    // Считывание команд пользователя.
    private int     workMode;                                                               // Режим работы (1- считываем из файла, 2- с консоли).
    private String  lastCmdLine= "";                                                        // Последняя считанная команда.
    final String CMD_EXIT= "exit";                                                  // Команда прерывания ввода данных.

    public CUseCalc (String nameFile) {
        try {
            scn= new Scanner(new File(nameFile));
            workMode= 1;
        } catch (FileNotFoundException e) {
            scn= new Scanner(System.in);
            workMode= 2;
            System.out.println("Вводите команды работы с калькулятором");
        }
    }

    public String getNextCmd() {
        lastCmdLine= scn.nextLine();
        return lastCmdLine;
    }

    public boolean isWokrs() {
        switch (workMode) {
            case 1: return scn.hasNext();
            case 2: return (!lastCmdLine.equals(CMD_EXIT));
            //defaul: return false;
        }
        return false;
    }

    public int buildCalc(String resName, CStackCalc calc) {
        Properties cmdList= new Properties();
        try {
            InputStreamReader readerCmdList= new InputStreamReader(CMain.class.getResourceAsStream(resName));
            cmdList.load(readerCmdList);
            Set cmdListSet= cmdList.keySet();
            String keyCmd;

            for (Iterator<Set> iCmdList= cmdListSet.iterator(); iCmdList.hasNext(); ) {
                keyCmd= String.valueOf(iCmdList.next());
                addCmdInCalc(calc, keyCmd, (String) cmdList.get(keyCmd));
            }
            return 0;
        }
        catch (Exception e) {
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.err.println("Не удалось загрузить ресурс с перечнем команд калькулятора!");
            return -1;
        }
    }

    private void addCmdInCalc(CStackCalc calc, String cmdName, String className) {
        ICmd cmd;
        Object o= null;
        Class cls= null;

        try {
            cls = Class.forName(className);
            o= cls.newInstance();
            if (o instanceof ICmd) {
                cmd= (ICmd) o;
                cmd.setCmdText(cmdName.toUpperCase());
                calc.addCmdCalc(cmd);
                System.out.println("Добавили команду в калькулятор (" + cmd.getCmdText() + ")!");
            }
        }
        catch (Exception e) {
            System.err.println("При создании команды для калькулятора использовано неизвестное имя файла (" + className + ")!");
        }

    }
}
