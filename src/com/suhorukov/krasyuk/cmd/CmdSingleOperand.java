package com.suhorukov.krasyuk.cmd;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class CmdSingleOperand extends AbstractCmd implements ICmd {

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {
        double op1= 0.0;                                       // Операнд для выполнения команды.

        if (dStak.size() > 0) {
            op1= dStak.pop();
            dStak.add(calcOperation(op1));
        }
        else
            System.err.println("В стеке не хватает операнда для проведения операции команды " + getCmdText() + "!");

        return 0;
    }

    public abstract double calcOperation(double operand1);
}
