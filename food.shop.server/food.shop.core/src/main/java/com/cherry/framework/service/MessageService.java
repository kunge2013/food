package com.cherry.framework.service;

import java.util.Map;

/**
 * 消息服务接口
 * 
 * @author 赵凡
 * @since 2017年11月23日
 * @version 1.0
 */
public interface MessageService {

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param to
	 *            接收者
	 */
	void sendEmail(String subject, String content, String to);
	
	/**
	 * 发送MIME邮件
	 * 
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @param to
	 *            接收者
	 * @param attas
	 *            附件列表
	 */
	void sendMimeEmail(String subject, String content, String to, String... attas);
	
	/**
	 * 发送短信
	 * 
	 * @param templateCode
	 *            短信模板编码
	 * @param templateParam
	 *            模板参数
	 * @param phoneNumbers
	 *            接收短信的手机号列表
	 */
	void sendSms(String templateCode, Map<String, Object> templateParam, String... phoneNumbers);
	
	// /**
	// * 发送短信验证码
	// *
	// * @param captcha 验证码
	// * @param phoneNumber 接收验证的手机号
	// */
	void sendCaptchaSms(String captcha, String phoneNumber);
	
}
