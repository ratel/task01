package com.suhorukov.krasyuk.task01;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class CMain {

    public static void main(String [] args) {
        CStackCalc stackCalc= new CStackCalc();                                          // Стековый калькулятор.
        String userCmd= "";                                                              // Введенная пользователем команда.
        CUseCalc useCalc;                                                                // Режим использования калькулятора.

        if (args.length > 0)
            useCalc= new CUseCalc(args[0]);
        else
            useCalc= new CUseCalc("");
        useCalc.buildCalc("CalcCommandList.properties", stackCalc);

        while (useCalc.isWokrs()) {
            userCmd= useCalc.getNextCmd();
            if (!userCmd.isEmpty())
                stackCalc.doCmd(userCmd);
        }
    }
}
