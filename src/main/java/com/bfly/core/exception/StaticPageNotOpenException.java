package com.bfly.core.exception;

/**
 * 
 * 内容静态页未开启异常
 * 
 */
public class StaticPageNotOpenException extends StaticPageException {
	public StaticPageNotOpenException() {
	}

	public StaticPageNotOpenException(String msg, Integer generated,
			String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
