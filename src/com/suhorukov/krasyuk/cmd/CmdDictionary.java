package com.suhorukov.krasyuk.cmd;

public class CmdDictionary extends AbstractCmd implements ICmd {

    @Override
    public void execute(String cmdLine) throws ExecuteException {
        String [] cmdWords= cmdLine.split("\\s");                                           // Разложенная по составляющим стрка параметров.

        if (cmdWords.length < 3) {
            throw new ExecuteException("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!");
            //System.err.println("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!");
        }
        else
            try {
                putInDictionary(cmdWords[1], Double.valueOf(cmdWords[2]));
                //System.out.println("В словарь добавлена константа " + cmdWords[1] + "= " + getValFromDictionary(cmdWords[1]) + ". Размер словаря = " + sizeDictionary() + ".");
            }
            catch (Exception e) {
                throw new ExecuteException("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!");
                //System.err.println("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!");
            }
    }
}
