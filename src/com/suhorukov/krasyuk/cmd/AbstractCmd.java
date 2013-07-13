package com.suhorukov.krasyuk.cmd;

import org.apache.log4j.Logger;

import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;
import org.apache.log4j.Priority;

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
    private Hashtable<String, Double> dictionaryDefine= null;                       // Словарь замен.
    private static final Logger log = Logger.getLogger(AbstractCmd.class);          // Логгер.

    enum LogLevelOut{OUTTRACE, OUTDEBUG, OUTINFO, OUTWARN, OUTERROR, OUTFATAL, OUTOFF};

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

    protected void outMessage(String message, LogLevelOut levelOut) {
        switch (levelOut) {
            case OUTTRACE:
                log.trace(message);
                break;
            case OUTDEBUG:
                log.debug(message);
                break;
            case OUTINFO:
                log.info(message);
                break;
            case OUTWARN:
                log.warn(message);
                break;
            case OUTERROR:
                log.error(message);
                break;
            case OUTFATAL:
                log.fatal(message);
                break;
            /*case OUTOFF:
                log.(message);
                break;*/
        }
    }

    protected boolean isLevelEnabled(LogLevelOut levelOut) {
        switch (levelOut) {
            case OUTTRACE:
                return log.isTraceEnabled();

            case OUTDEBUG:
                return log.isDebugEnabled();

            case OUTINFO:
                return log.isInfoEnabled();

            case OUTWARN:
            case OUTERROR:
            case OUTFATAL:
            default:
                return true;
        }
    }

    public void outStackInLog() {
        if (isLevelEnabled(LogLevelOut.OUTDEBUG)) {
            StringBuilder outStr = new StringBuilder();

            if (dataStack != null)
                for (Double iStack: dataStack) {
                    outStr.append(iStack);
                    outStr.append(";  ");
                }
            else
                outStr.append("Stack is null.");

            if (outStr.length() == 0)
                outStr.append("Stack is empty.");

            outMessage(String.valueOf(outStr), LogLevelOut.OUTDEBUG);
        }
    }

    public void outContextInLog() {
        if (isLevelEnabled(LogLevelOut.OUTDEBUG)) {
            StringBuilder outStr = new StringBuilder();

            if (dictionaryDefine != null)
                for (Map.Entry<String, Double> iDictionary: dictionaryDefine.entrySet()) {
                    outStr.append(iDictionary.getKey() + " = " + iDictionary.getValue());
                    outStr.append(";  ");
                }
            else
                outStr.append("Context is null.");

            if (outStr.length() == 0)
                outStr.append("Context is empty.");

            outMessage(String.valueOf(outStr), LogLevelOut.OUTDEBUG);
        }
    }
}
