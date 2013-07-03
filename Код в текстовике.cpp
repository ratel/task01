package com.suhorukov.krasyuk.task01;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class CMain {
    public static String CMD_EXIT= "exit";

    public static void main(String [] args) {
        Scanner scn= new Scanner(System.in);
        CStackCalc stackCalc= new CStackCalc();
        String userCmd;
        CCmdPush cmdCalc;

        //Hashtable<String, String> cmdMap;
        //cmdMap= new Hashtable<String, String>();
        //cmdMap.put("String1", "Str2");
        //System.out.println(cmdMap.get("String1"));

        cmdCalc= new CCmdPush();
        stackCalc.addCmdCalc(cmdCalc);
        //cmdCalc.
        stackCalc.addCmdCalc(cmdCalc.dictionary);
        stackCalc.addCmdCalc((new CCmdPop()));
        stackCalc.addCmdCalc((new CCmdPrintTopElement()));
        stackCalc.addCmdCalc((new CCmdPlus()));
        stackCalc.addCmdCalc((new CCmdMinus()));
        stackCalc.addCmdCalc((new CCmdMul()));
        stackCalc.addCmdCalc((new CCmdDiv()));

        stackCalc.addCmdCalc((new CCmdSQRT()));

        System.out.println("Вводите команды работы с калькулятором");
        while (true) {
            userCmd= scn.nextLine();
            if (userCmd.equals(CMD_EXIT))
                break;

            if (!userCmd.isEmpty())
                stackCalc.doCmd(userCmd);
        }
    }
}


//----------------------------------------------------------------------------------------------------

package com.suhorukov.krasyuk.task01;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class CStackCalc {
    Stack<Double>   dataStack;
    Hashtable<String, CAbstractCmd>     cmdTable;                             // Поддерживаемые команды.


    public CStackCalc() {
        dataStack= new Stack<Double>();
        cmdTable= new Hashtable<String, CAbstractCmd>();
    }

    public void doCmd(String cmdLine) {
        CAbstractCmd cmd= null;
//        StringTokenizer st = new StringTokenizer(cmdLine);
//        if (st.hasMoreTokens())
//            cmd= cmdTable.get(st.nextToken());
        String [] cmdWords= cmdLine.split("\\s");

        if (cmdWords.length > 0)
            cmd= cmdTable.get(cmdWords[0].toUpperCase());

        if (cmd != null) {
            cmd.execute(dataStack, cmdLine);
            System.out.println("Размер массива = " + dataStack.size());
        }
        else
            System.out.println("Введенна неизвестная команда- \"" + cmdLine + "\"!");

    }

    public void addCmdCalc(CAbstractCmd cmdItem) {
        if (cmdItem != null)
            cmdTable.put(cmdItem.getCmdText(), cmdItem);
    }
}



//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public interface CAbstractCmd {
   // static String cmdText= null;
    public int execute(Stack<Double> dStak, String cmdLine);
    public String getCmdText();
}

//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class CCmdSingleOperand implements CAbstractCmd {
//    String cmdText = "SINGLE_OPERAND";

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {
        double op1= 0.0;

        if (dStak.size() > 0) {
            op1= dStak.pop();
            dStak.add(calcOperation(op1));
        }
        else
            System.err.println("В стеке не хватает операнда для проведения операции!");

        return 0;
    }

    public abstract double calcOperation(double operand1);
}


//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class CCmdTwoOperatds implements CAbstractCmd {
//    String cmdText = "TWO_OPERAND";

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {
        double op1= 0.0, op2= 0.0;

        if (dStak.size() < 2)
            System.err.println("В стеке не хватает операнда для проведения операции команды " + getCmdText() + "!");
        else {
            op1= dStak.pop();
            op2= dStak.pop();
            dStak.add(calcOperation(op1, op2));
        }

        return 0;
    }

    public abstract double calcOperation(double operand1, double operand2);
}


//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;


import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class CCmdPush implements CAbstractCmd {
    final String cmdText = "PUSH";
    Hashtable<String, Double> dictionaryDefine= null;                             // Словарь констант.
    CCmdDictionary dictionary= new CCmdDictionary();


    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {
        String [] cmdWords= cmdLine.split("\\s");
        Double valueFromDictionary;

        if (cmdWords.length < 2) {
            System.err.println("Не введено данных для команды вставки (PUSH)");
        }
        else
            try {
                dStak.add(Double.valueOf(cmdWords[1]));
            }
            catch (Exception e) {
                if (dictionaryDefine != null) {
                    valueFromDictionary= dictionaryDefine.get(cmdWords[1]);
                    if (valueFromDictionary != null) {
                        dStak.add(valueFromDictionary);
                        return 0;
                    }
                }
                System.err.println("Параметром для команды PUSH должно быть вещественное число!");
                System.out.println("размер словаря = " + dictionaryDefine.size());
            }

        return 0;
    }

    public String getCmdText() {
        return cmdText;
    }

    public CCmdDictionary getCmdDictionary() {
        return dictionary;
    }



    public class CCmdDictionary implements CAbstractCmd {
       final String cmdText = "DEFINE";

        CCmdDictionary() {
            dictionaryDefine= new Hashtable<String, Double>();
        }

        @Override
        public int execute(Stack<Double> dStak, String cmdLine) {
            String [] cmdWords= cmdLine.split("\\s");

            if (dictionaryDefine != null)
            {
                if (cmdWords.length < 3) {
                    System.err.println("Формат команды " + getCmdText() + ":  " + getCmdText() + " CONST VALUE!");
                }
                else
                    try {
                        dictionaryDefine.put(cmdWords[1], Double.valueOf(cmdWords[2]));
                    }
                    catch (Exception e) {
                        System.err.println("Второй параметр VALUE команды " + getCmdText() + " должен быть вещественным числом!");
                    }
                System.out.println("размер словаря = " + dictionaryDefine.size());

            }

            return 0;
        }

        @Override
        public String getCmdText() {
            return cmdText;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}


//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class CCmdPop implements CAbstractCmd {
    final String cmdText = "POP";

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {

        if (dStak.size() > 0) {
            dStak.pop();
        }

        return 0;
    }

    public String getCmdText() {
        return cmdText;
    }
}

//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class CCmdPrintTopElement implements CAbstractCmd {
    final String cmdText = "PRINT";

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {

        if (dStak.size() > 0) {
            System.out.println("Верхний элемент в стеке равен " + dStak.peek());
        }

        return 0;
    }

    public String getCmdText() {
        return cmdText;
    }
}


//----------------------------------------------------------------------------------------------------
package com.suhorukov.krasyuk.task01;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class CCmdMinus extends CCmdTwoOperatds {
    final String cmdText = "-";

    @Override
    public double calcOperation(double operand1, double operand2) {
        return (operand1 - operand2);
    }

   public String getCmdText() {
        return cmdText;
    }
}


//----------------------------------------------------------------------------------------------------  
package com.suhorukov.krasyuk.task01;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class CCmdPlus  extends CCmdTwoOperatds {
    final String cmdText= "+";

    @Override
    public double calcOperation(double operand1, double operand2) {
        return (operand1 + operand2);
    }

    public String getCmdText() {
        return cmdText;
    }
}

//----------------------------------------------------------------------------------------------------  
package com.suhorukov.krasyuk.task01;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class CCmdMul extends CCmdTwoOperatds {
    final String cmdText = "*";

    @Override
    public double calcOperation(double operand1, double operand2) {
        return (operand1 * operand2);
    }

    public String getCmdText() {
        return cmdText;
    }
}


//----------------------------------------------------------------------------------------------------  
package com.suhorukov.krasyuk.task01;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class CCmdDiv extends CCmdTwoOperatds {
    final String cmdText = "/";

    @Override
    public double calcOperation(double operand1, double operand2) {
        return (operand1 / operand2);
    }

    public String getCmdText() {
        return cmdText;
    }
}


//----------------------------------------------------------------------------------------------------  
package com.suhorukov.krasyuk.task01;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class CCmdSQRT extends CCmdSingleOperand {
    final String cmdText = "SQRT";

    @Override
    public double calcOperation(double operand1) {
        return (Math.sqrt(operand1));
    }

    public String getCmdText() {
        return cmdText;
    }
}


//----------------------------------------------------------------------------------------------------  
//----------------------------------------------------------------------------------------------------  



