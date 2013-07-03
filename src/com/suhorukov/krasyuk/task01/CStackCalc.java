package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.ICmd;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class CStackCalc {
    Stack<Double>           dataStack;                            // Стек значений
    Hashtable<String, ICmd> cmdTable;                             // Поддерживаемые команды.


    public CStackCalc() {
        dataStack= new Stack<Double>();
        cmdTable= new Hashtable<String, ICmd>();
    }

    public void doCmd(String cmdLine) {
        ICmd cmd= null;
//        StringTokenizer st = new StringTokenizer(cmdLine);
//        if (st.hasMoreTokens())
//            cmd= cmdTable.get(st.nextToken());
        String [] cmdWords= cmdLine.split("\\s");

        if (cmdWords.length > 0)
            cmd= cmdTable.get(cmdWords[0].toUpperCase());

        if (cmd != null) {
            cmd.execute(dataStack, cmdLine);
        }
        else
            System.out.println("Введенна неизвестная команда- \"" + cmdLine + "\"!");
    }

    public void addCmdCalc(ICmd cmdItem) {
        if (cmdItem != null)
            cmdTable.put(cmdItem.getCmdText(), cmdItem);
    }
}

