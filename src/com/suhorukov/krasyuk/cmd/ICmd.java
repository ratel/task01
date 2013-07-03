package com.suhorukov.krasyuk.cmd;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 27.06.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public interface ICmd {
   // static String cmdText= null;
    public int execute(Stack<Double> dStak, String cmdLine);
    public String getCmdText();
    public void setCmdText(String text);
}
