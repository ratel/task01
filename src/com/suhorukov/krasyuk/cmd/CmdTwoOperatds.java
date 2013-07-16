package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class CmdTwoOperatds extends AbstractCmd implements ICmd {

    @Override
    public void execute(String cmdLine) throws ExecuteException {
        double op1, op2;                                       // Операнды для выполнения команды.

        if (sizeStack() < 2) {
            throw new ExecuteException("В стеке не хватает операнда для проведения операции команды " + getCmdText() + "!");
        }
        else {
            op1= popElementStack();
            op2= popElementStack();
            addElementStack(calcOperation(op1, op2));
        }
    }

    protected abstract double calcOperation(double operand1, double operand2);
}
