package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class CmdSQRT extends CmdSingleOperand {

    @Override
    public double calcOperation(double operand1) {
        return (Math.sqrt(operand1));
    }
}
