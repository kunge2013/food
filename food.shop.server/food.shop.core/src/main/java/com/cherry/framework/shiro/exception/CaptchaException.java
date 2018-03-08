package com.cherry.framework.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码错误
 * 
 * @author 赵凡
 * @since 2017年11月24日
 * @version 1.0
 */
public class CaptchaException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8864704746194509526L;

	public CaptchaException() {
		super();
	}

	public CaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaException(String message) {
		super(message);
	}

	public CaptchaException(Throwable cause) {
		super(cause);
	}

}
