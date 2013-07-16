package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class CmdPrintTopElement extends AbstractCmd implements ICmd {

    @Override
    public void execute(String cmdLine) {

        if (sizeStack() > 0) {
            System.out.println("Верхний элемент в стеке равен " + peekElementStack());
        }
        else
            System.out.println("Стек пуст!");
    }
}
