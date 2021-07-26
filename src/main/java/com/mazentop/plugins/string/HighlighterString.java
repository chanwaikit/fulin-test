package com.mazentop.plugins.string;

/**
 * @Author: Lifl
 * @Date: 2019/12/19 17:30
 */
public class HighlighterString {

    private StringBuffer stringBuffer;

    public HighlighterString() {
        this("");
    }

    public HighlighterString(String s) {
        this.stringBuffer = new StringBuffer(s);
    }

    public StringBuffer appendH(String s) {
        return this.stringBuffer.append("<fonts style='color:red'>").append(s).append("</fonts>");
    }

    public StringBuffer append(String s) {
        return this.stringBuffer.append(s);
    }

    @Override
    public String toString() {
        return stringBuffer.toString();
    }
}
