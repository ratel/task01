package com.suhorukov.krasyuk.task01;

import com.suhorukov.krasyuk.cmd.ICmd;
import org.apache.log4j.Logger;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */

public class CStackCalc {
    private Hashtable<String, ICmd> cmdTable;                                               // Поддерживаемые команды.
    private static final Logger log = Logger.getLogger(CStackCalc.class);                   // Логгер.

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
            cmd.execute(cmdLine);
        }
        else
            log.info("Введенна неизвестная команда- \"" + cmdLine + "\"!");
    }

    public void addCmdCalc(ICmd cmdItem) {
        if (cmdItem != null)
            cmdTable.put(cmdItem.getCmdText(), cmdItem);
    }
}

