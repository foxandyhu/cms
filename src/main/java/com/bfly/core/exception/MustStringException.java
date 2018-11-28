package com.bfly.core.exception;

import freemarker.template.TemplateModelException;

/**
 * 非数字参数异常
 */
public class MustStringException extends TemplateModelException {
	public MustStringException(String paramName) {
		super("The \"" + paramName + "\" parameter must be a string.");
	}
}
