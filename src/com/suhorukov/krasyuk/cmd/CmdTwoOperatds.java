package com.suhorukov.krasyuk.cmd;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class CmdTwoOperatds extends AbstractCmd implements ICmd {

    @Override
    public int execute(String cmdLine) {
        double op1= 0.0, op2= 0.0;                                       // Операнды для выполнения команды.

        if (dataStack.size() < 2)
            System.err.println("В стеке не хватает операнда для проведения операции команды " + getCmdText() + "!");
        else {
            op1= dataStack.pop();
            op2= dataStack.pop();
            dataStack.add(calcOperation(op1, op2));
        }

        return 0;
    }

    public abstract double calcOperation(double operand1, double operand2);
}
