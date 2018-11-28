package com.bfly.core.exception;

/**
 * 未生成静态页异常
 */
public class GeneratedZeroStaticPageException extends StaticPageException {
    public GeneratedZeroStaticPageException() {
    }

    public GeneratedZeroStaticPageException(String msg) {
        super(msg, 0);
    }
}
