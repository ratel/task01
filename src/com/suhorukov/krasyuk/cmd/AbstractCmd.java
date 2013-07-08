package com.suhorukov.krasyuk.cmd;

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
    private Stack<Double> dataStack= null;                                          // Стек значений
    @FieldCmd(fieldType= fieldCmdKind.CONTEXT)
    private Hashtable<String, Double> dictionaryDefine= null;

    public String getCmdText() {
        return cmdText;
    }

    public void setCmdText(String text) {
        cmdText= text;
    }

    protected Double popElementStack () {
        if (dataStack != null)
            return dataStack.pop();
        else
            return 0.0;
    }

    protected Double peekElementStack () {
        if (dataStack != null)
            return dataStack.peek();
        else
            return 0.0;
    }

    protected void addElementStack (Double value) {
        if (dataStack != null)
            dataStack.add(value);
    }

    protected int sizeStack () {
        if (dataStack != null)
            return dataStack.size();
        else
            return 0;
    }

    protected Double getValFromDictionary (String key) {
        if (dictionaryDefine != null)
            return dictionaryDefine.get(key);

        return null;
    }

    protected void putInDictionary (String key, Double value) {
        if (dictionaryDefine != null)
            dictionaryDefine.put(key, value);
    }

    protected int sizeDictionary () {
        if (dictionaryDefine != null)
            return dictionaryDefine.size();
        else
            return 0;
    }
}
