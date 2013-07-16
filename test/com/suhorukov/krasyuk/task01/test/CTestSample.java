package com.suhorukov.krasyuk.task01.test;

import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 03.07.13
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
public class CTestSample extends TestCase {
    public void setUp() throws Exception {

    }

    public void tearDown()throws Exception {
    }

    public void testName() throws Exception {
        String s= "a" + "bc";
        assertEquals("abc", s);
        double x= 0.0;
        double y= 0.0;
        assertEquals(x, y);

    /*    CStackCalc stackCalc= new CStackCalc();                                             // Стековый калькулятор.
        CalcManager calcManager;                                                            // Режим использования калькулятора.
        String userCmd;                                                                     // Введенная пользователем команда.
        Scanner scn= null;                                                                  // Считывание команд пользователя.

        try {
            scn= new Scanner(new File("res//1.txt"));
            calcManager= new CalcManager(scn, CalcManager.ProxyMode.PROXYIN);
        }
        catch (FileNotFoundException e) {
            System.err.println("Не удалось открыть файл \"" + "res//1.txt" + "\"");
            workMode= CalcManager.WorkType.DONTWORK;
        }

        InputStreamReader readerCmdList;                                                    // Ресурс с командами для калькулятора.
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
        }     */
    }
}
