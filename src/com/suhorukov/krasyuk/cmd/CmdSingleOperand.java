package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class CmdSingleOperand extends AbstractCmd implements ICmd {

    @Override
    public void execute(String cmdLine) throws ExecuteException {
        double op1;                                                                         // Операнд для выполнения команды.

        if (sizeStack() > 0) {
            op1= popElementStack();
            addElementStack(calcOperation(op1));
        }
        else
            throw new ExecuteException("В стеке не хватает операнда для проведения операции команды " + getCmdText() + "!");
    }

    protected abstract double calcOperation(double operand1);
}
