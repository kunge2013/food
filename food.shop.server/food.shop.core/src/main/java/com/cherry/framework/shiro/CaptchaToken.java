package com.cherry.framework.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 带验证码的Token
 * 
 * @author 赵凡
 * @since 2017年11月24日
 * @version 1.0
 */
public class CaptchaToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2555047603275857646L;
	
	// 图形验证码
	private String captcha;
	
	/**
	 * 创建一个有图形验证码功能的Token
	 * 
	 */
	public CaptchaToken() {
		super();
	}

	/**
	 * 创建一个有图形验证码功能的Token
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param rememberMe
	 *            记住我
	 * @param host
	 *            主机
	 * @param captcha
	 *            图形验证码
	 */
	public CaptchaToken(String username, String password, boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	/**
	 * 获取图形验证码
	 * 
	 * @return 图形验证码
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * 设置图形验证码
	 * 
	 * @param captcha
	 *            图形验证码
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
