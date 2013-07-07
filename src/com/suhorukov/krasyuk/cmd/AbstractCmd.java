package com.suhorukov.krasyuk.cmd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Krasyuk
 * Date: 03.07.13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */


public abstract class AbstractCmd implements ICmd {
    private String cmdText = "ACmd";                                                // Текстовая интерпретация команды реализованной в классе.
    @FieldCmd(fieldType= fieldCmdKind.STACK)
    protected Stack<Double> dataStack= null;                                        // Стек значений
    @FieldCmd(fieldType= fieldCmdKind.CONTEXT)
    protected Hashtable<String, Double> dictionaryDefine= null;

    public String getCmdText() {
        return cmdText;
    }

    public void setCmdText(String text) {
        cmdText= text;
    }
}
