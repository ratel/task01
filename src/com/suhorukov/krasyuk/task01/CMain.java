package com.suhorukov.krasyuk.task01;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class CMain {

    public static void main(String [] args) {
        CStackCalc stackCalc= new CStackCalc();                                         // Стековый калькулятор.
        String userCmd;                                                                 // Введенная пользователем команда.
        CalcManager calcManager;                                                        // Режим использования калькулятора.
        InputStreamReader readerCmdList;                                                // Ресурс с командами для калькулятора.

        if (args.length > 0){
            calcManager= new CalcManager(new File(args[0]), CalcManager.ProxyMode.PROXYIN);
        }
        else
            calcManager= new CalcManager(System.in, CalcManager.ProxyMode.PROXYIN);

        readerCmdList= new InputStreamReader(CMain.class.getResourceAsStream("CalcCommandList.properties"));
        calcManager.buildCalc(readerCmdList, stackCalc);

        try {
            readerCmdList.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        while (calcManager.isWorks()) {
            userCmd= calcManager.getNextCmd();

            if (!userCmd.isEmpty())
                stackCalc.doCmd(userCmd);
        }
    }
}
