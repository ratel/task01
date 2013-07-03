package com.suhorukov.krasyuk.cmd;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class CmdPrintTopElement extends AbstractCmd implements ICmd {

    @Override
    public int execute(Stack<Double> dStak, String cmdLine) {

        if (dStak.size() > 0) {
            System.out.println("Верхний элемент в стеке равен " + dStak.peek());
        }
        else
            System.out.println("Стек пуст!");

        return 0;
    }
}
