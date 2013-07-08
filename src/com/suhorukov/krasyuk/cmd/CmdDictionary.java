package com.suhorukov.krasyuk.cmd;

public class CmdDictionary extends AbstractCmd implements ICmd {

    @Override
    public int execute(String cmdLine) {
        String [] cmdWords= cmdLine.split("\\s");

        if (cmdWords.length < 3) {
            System.err.println("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!");
        }
        else
            try {
                putInDictionary(cmdWords[1], Double.valueOf(cmdWords[2]));
                System.out.println("В словарь добавлена константа " + cmdWords[1] + "= " +
                        getValFromDictionary(cmdWords[1]) + ". Размер словаря = " + sizeDictionary() + ".");
            }
            catch (Exception e) {
                System.err.println("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!");
            }

        return 0;
    }
}
