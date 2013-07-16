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
    public void execute(String cmdLine) throws ExecuteException {
        String [] cmdWords= cmdLine.split("\\s");                                           // Разложенная по составляющим стрка параметров.
        Double valueFromDictionary;                                                         // Значение из словаря замен.

        if (cmdWords.length < 2) {
            //System.err.println("Не введено данных для команды вставки (" + getCmdText() + ").");
            throw new ExecuteException("Не введено данных для команды вставки (" + getCmdText() + ").");
        }
        else {
            try {
                addElementStack(Double.valueOf(cmdWords[1]));
            }
            catch (Exception e) {
                valueFromDictionary= getValFromDictionary(cmdWords[1]);

                if (valueFromDictionary != null) {
                    addElementStack(valueFromDictionary);
                    return;
                }

                throw new ExecuteException("Параметром для команды PUSH должно быть вещественное число!");
            }
        }
    }
}

