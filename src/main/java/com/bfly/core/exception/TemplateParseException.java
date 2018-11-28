package com.bfly.core.exception;

/**
 * 
 * 模板解析异常
 * 
 */
public class TemplateParseException extends StaticPageException {
	public TemplateParseException() {
	}

	public TemplateParseException(String msg, Integer generated, String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
