package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 03.07.13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCmd implements ICmd {
    private String cmdText = "ACmd";                                                // Текстовая интерпретация команды реализованной в классе.

    public String getCmdText() {
        return cmdText;
    }

    public void setCmdText(String text) {
        cmdText= text;
    }
}
