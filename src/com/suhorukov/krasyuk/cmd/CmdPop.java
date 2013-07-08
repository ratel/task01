package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 28.06.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class CmdPop extends AbstractCmd implements ICmd {

    @Override
    public int execute(String cmdLine) {
        if (sizeStack() > 0) {
            popElementStack();
        }

        return 0;
    }
}
