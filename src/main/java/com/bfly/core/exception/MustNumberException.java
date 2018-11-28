package com.bfly.core.exception;

import freemarker.template.TemplateModelException;

/**
 * 非数字参数异常
 */
public class MustNumberException extends TemplateModelException {
	public MustNumberException(String paramName) {
		super("The \"" + paramName + "\" parameter must be a number.");
	}
}
