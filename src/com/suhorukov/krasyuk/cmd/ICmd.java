package com.suhorukov.krasyuk.cmd;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public interface ICmd {
    public void execute(String cmdLine) throws ExecuteException;
    public String getCmdText();
    public void setCmdText(String text);
}
