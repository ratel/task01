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
    public int execute(String cmdLine) {

        if (sizeStack() > 0) {
            if (isLevelEnabled(LogLevelOut.OUTINFO))
                outMessage("Верхний элемент в стеке равен " + peekElementStack(), LogLevelOut.OUTINFO);
            //System.out.println("Верхний элемент в стеке равен " + peekElementStack());
        }
        else
            if (isLevelEnabled(LogLevelOut.OUTINFO))
                outMessage("Стек пуст!", LogLevelOut.OUTINFO);
            //System.out.println("Стек пуст!");

        return 0;
    }
}
