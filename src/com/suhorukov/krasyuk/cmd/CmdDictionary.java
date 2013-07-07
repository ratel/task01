package com.suhorukov.krasyuk.cmd;

import java.util.Hashtable;
import java.util.Stack;

public class CmdDictionary extends AbstractCmd implements ICmd {
//       String cmdText = "DEFINE";                                                // Текстовая интерпретация команды реализованной в классе.

    @Override
    public int execute(String cmdLine) {
        String [] cmdWords= cmdLine.split("\\s");

        if (dictionaryDefine != null)
        {
            if (cmdWords.length < 3) {
                System.err.println("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!");
            }
            else
                try {
                    dictionaryDefine.put(cmdWords[1], Double.valueOf(cmdWords[2]));
                    System.out.println("В словарь добавлена константа " + cmdWords[1] + "= " +
                            dictionaryDefine.get(cmdWords[1]) + ". Размер словаря = " + dictionaryDefine.size());
                }
                catch (Exception e) {
                    System.err.println("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!");
                }
        }

        return 0;
    }
}
