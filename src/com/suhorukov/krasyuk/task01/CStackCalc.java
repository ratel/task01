package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.ExecuteException;
import com.suhorukov.krasyuk.cmd.ICmd;

import java.util.Hashtable;


/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */

public class CStackCalc {
    private Hashtable<String, ICmd> cmdTable;                                               // Поддерживаемые команды.

    public CStackCalc() {
        cmdTable= new Hashtable<>();
    }

    public void doCmd(String cmdLine) {
        ICmd cmd= null;                                                                     // Команда которая будет исполнена.
//        StringTokenizer st = new StringTokenizer(cmdLine);
//        if (st.hasMoreTokens())
//            cmd= cmdTable.get(st.nextToken());
        String [] cmdWords= cmdLine.split("\\s");                                           // Строка команды, разбитая на слова.

        if (cmdWords.length > 0)
            cmd= cmdTable.get(cmdWords[0].toUpperCase());

        if (cmd != null) {
            try {
                cmd.execute(cmdLine);
            } catch (ExecuteException e) {
                e.printStackTrace();
                //System.err.println(e.getMessage());
            }
        }
        else
            System.out.println("Введена неизвестная команда- \"" + cmdLine + "\"!");
    }

    public void addCmdCalc(ICmd cmdItem) {
        if (cmdItem != null)
            cmdTable.put(cmdItem.getCmdText(), cmdItem);
    }
}

