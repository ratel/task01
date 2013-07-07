package com.suhorukov.krasyuk.cmd;


import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class CmdPush extends AbstractCmd implements ICmd {

    @Override
    public int execute(String cmdLine) {
        String [] cmdWords= cmdLine.split("\\s");
        Double valueFromDictionary;

        if (cmdWords.length < 2) {
            System.err.println("Не введено данных для команды вставки (" + getCmdText() + ").");
        }
        else {
            try {
                dataStack.add(Double.valueOf(cmdWords[1]));
            }
            catch (Exception e) {
                if (dictionaryDefine != null) {
                    valueFromDictionary= dictionaryDefine.get(cmdWords[1]);

                    if (valueFromDictionary != null) {
                        dataStack.add(valueFromDictionary);
                        return 0;
                    }
                }
                System.err.println("Параметром для команды PUSH должно быть вещественное число!");
            }
        }

        return 0;
    }
}

