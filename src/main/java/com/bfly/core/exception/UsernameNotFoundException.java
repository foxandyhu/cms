package com.bfly.core.exception;

/**
 * 用户名没有找到异常
 */
public class UsernameNotFoundException extends AuthenticationException {
	public UsernameNotFoundException() {
	}

	public UsernameNotFoundException(String msg) {
		super(msg);
	}
}