package com.suhorukov.krasyuk.cmd;

public class CmdDictionary extends AbstractCmd implements ICmd {

    @Override
    public int execute(String cmdLine) {
        String [] cmdWords= cmdLine.split("\\s");                                           // Разложенная по составляющим стрка параметров.

        if (cmdWords.length < 3) {
            outMessage("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!", LogLevelOut.OUTWARN);
        }
        else
            try {
                putInDictionary(cmdWords[1], Double.valueOf(cmdWords[2]));

                if (isLevelEnabled(LogLevelOut.OUTINFO))
                    outMessage("В словарь добавлена константа " + cmdWords[1] + "= " +
                        getValFromDictionary(cmdWords[1]) + ". Размер словаря = " + sizeDictionary() + ".",
                        LogLevelOut.OUTINFO);
            }
            catch (Exception e) {
                outMessage("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!", LogLevelOut.OUTWARN);
            }

        return 0;
    }
}
