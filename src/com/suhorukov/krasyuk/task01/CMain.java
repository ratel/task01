package com.suhorukov.krasyuk.task01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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
        CalcManager calcManager;                                                        // Менеджер управления калькулятором.
        InputStreamReader readerCmdList= null;                                          // Ресурс с командами для калькулятора.
        Scanner scn= null;                                                              // Считывание команд пользователя.
        CalcManager.WorkType workMode;                                                  // Режим работы (1- считываем из файла, 2- с консоли).

        if (args.length > 0){
            try {
                scn= new Scanner(new File(args[0]));
                workMode= CalcManager.WorkType.WHILEREAD;
            }
            catch (FileNotFoundException e) {
                System.err.println("Не удалось открыть файл \"" + args[0] + "\"");
                workMode= CalcManager.WorkType.DONTWORK;
            }
        }
        else {
            scn= new Scanner(System.in);
            workMode= CalcManager.WorkType.NOTEXIT;
        }

        calcManager= new CalcManager(scn, workMode, CalcManager.ProxyMode.PROXYIN);

        try {
            readerCmdList= new InputStreamReader(CMain.class.getResourceAsStream("CalcCommandList.properties"));
            calcManager.buildCalc(readerCmdList, stackCalc);
        }
        catch (IOException e) {
            System.err.println("Не удалось загрузить ресурс с перечнем команд калькулятора!");
        }
        finally {
            if (readerCmdList != null)
                try {
                    readerCmdList.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
        }


        while (calcManager.isWorks()) {
            userCmd= calcManager.getNextCmd();

            if (!userCmd.isEmpty())
                stackCalc.doCmd(userCmd);
        }

        if (scn != null)
            scn.close();
    }
}
