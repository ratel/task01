package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class CmdMinus extends CmdTwoOperatds {

    @Override
    public double calcOperation(double operand1, double operand2) {
        return (operand1 - operand2);
    }
}
