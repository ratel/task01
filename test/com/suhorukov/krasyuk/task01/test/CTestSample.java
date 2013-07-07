package com.suhorukov.krasyuk.task01.test;

import com.suhorukov.krasyuk.task01.CStackCalc;
import com.suhorukov.krasyuk.task01.CUseCalc;
import junit.framework.TestCase;

import java.io.File;

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

        CStackCalc stackCalc= new CStackCalc();                                          // Стековый калькулятор.
        CUseCalc useCalc;                                                                // Режим использования калькулятора.
        String userCmd= "";                                                              // Введенная пользователем команда.

        useCalc= new CUseCalc(new File("res//1.txt"), CUseCalc.WorkType.WHILEREAD);
        useCalc.buildCalc("CalcCommandList", stackCalc);

        while (useCalc.isWokrs()) {
            userCmd= useCalc.getNextCmd();
            if (!userCmd.isEmpty())
                stackCalc.doCmd(userCmd);
        }
    }
}
