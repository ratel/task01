package com.suhorukov.krasyuk.cmd;


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
        String [] cmdWords= cmdLine.split("\\s");                                           // Разложенная по составляющим стрка параметров.
        Double valueFromDictionary;                                                         // Значение из словаря замен.

        if (cmdWords.length < 2) {
            outMessage("Не введено данных для команды вставки (" + getCmdText() + ").", LogLevelOut.OUTWARN);
        }
        else {
            try {
                addElementStack(Double.valueOf(cmdWords[1]));
            }
            catch (Exception e) {
                valueFromDictionary= getValFromDictionary(cmdWords[1]);

                if (valueFromDictionary != null) {
                    addElementStack(valueFromDictionary);
                    return 0;
                }

                outMessage("Параметром для команды PUSH должно быть вещественное число!", LogLevelOut.OUTWARN);
            }
        }

        return 0;
    }
}

