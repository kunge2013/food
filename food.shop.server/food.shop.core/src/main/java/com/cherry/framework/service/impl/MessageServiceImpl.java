package com.cherry.framework.service.impl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cherry.framework.service.MessageService;
import com.cherry.framework.utils.SpringIoC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 消息服务实现类
 * 
 * @author 赵凡
 * @since 2017年11月23日
 * @version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
//	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${email.username}")
	private String from;
	
	@Value("${aliyun.dysmsapi.accessKeyId}")
	private String accessKeyId;
	
	@Value("${aliyun.dysmsapi.accessKeySecret}")
	private String accessKeySecret;
	
	@Value("${aliyun.dysmsapi.signName}")
	private String signName;
	
	private IAcsClient acsClient;
	
	@Async
	@Override
	public void sendEmail(String subject, String content, String to) {
		SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(from);// 发件人
        message.setTo(to);// 收件人
        message.setSubject(subject);// 主题
        message.setText(content);// 正文
        mailSender.send(message);
	}
	
	@Async
	@Override
	public void sendMimeEmail(String subject, String content, String to, String... attas) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from);// 发件人
			helper.setTo(to);// 收件人
			helper.setSubject(subject);// 主题
			helper.setText(content, true);// 正文
			if (ArrayUtils.isNotEmpty(attas)) {
				for (String attaUrl : attas) {
					URL url = new URL(attaUrl);
					Path path = Paths.get(url.toURI());
					URLDataSource dataSource = new URLDataSource(url);
					helper.addAttachment(path.getFileName().toString(), dataSource);
				}
			}
			mailSender.send(message);
		} catch (MessagingException | MalformedURLException | URISyntaxException e) {
			logger.error("生成邮件失败：【subject:" + subject + "|||content:" + content + "|||to:" + to + "|||attas:" + ArrayUtils.toString(attas) + "】", e);
		}
	}

	@Async
	@SuppressWarnings("unchecked")
	@Override
	public void sendSms(String templateCode, Map<String, Object> templateParam, String... phoneNumbers) {
		if (ArrayUtils.isNotEmpty(phoneNumbers)) {
			String numberStr = ArrayUtils.toString(phoneNumbers);
			numberStr = numberStr.substring(1, numberStr.length() - 1);
			ObjectMapper objectMapper = SpringIoC.getBean("objectMapper", ObjectMapper.class);
			try {
				if (templateParam == null) {
					templateParam = Collections.EMPTY_MAP;
				}
				String param = objectMapper.writeValueAsString(templateParam);
				SendSmsRequest request = new SendSmsRequest();
				request.setMethod(MethodType.POST);
				request.setPhoneNumbers(numberStr);
				request.setSignName(signName);
				request.setTemplateCode(templateCode);
				request.setTemplateParam(param);
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
					logger.debug("发送短信成功！短信模板[{}]，短信模板参数[{}]，接收手机号[{}]", templateCode, param, numberStr);
				} else {
					logger.error("发送短信失败！短信模板[{}]，短信模板参数[{}]，接收手机号[{}]->" + sendSmsResponse.getMessage(), templateCode, param, numberStr);
				}
			} catch (JsonProcessingException e) {
				logger.error("解析短信模板参数失败！", e);
			} catch (ServerException e) {
				logger.error("短信服务异常！", e);
			} catch (ClientException e) {
				logger.error("短信客户端异常！", e);
			}
		}
	}

	@Override
	public void sendCaptchaSms(String captcha, String phoneNumber) {
		String templateCode = "SMS_121161336";
		Map<String, Object> templateParam = new HashMap<>();
		templateParam.put("code", captcha);
		sendSms(templateCode, templateParam, phoneNumber);
	}

	// 所有属性设置完成之后调用
	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化短信服务
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
			acsClient = new DefaultAcsClient(profile);
		} catch (ClientException e) {
			logger.error("初始化短信服务失败！", e);
		}
	}
	
	public static void main(String[] args) throws ClientException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		final String product = "Dysmsapi";
		final String domain = "dysmsapi.aliyuncs.com";
		final String accessKeyId = "LTAI1HxALCnRO2F8";
		final String accessKeySecret = "M3m6Wj3PY5tCMxHWGO8VAjdUdxfyEo";
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		DefaultAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers("18972836139");
		//必填:短信签名-可在短信控制台中找到
		request.setSignName("云通信");
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode("SMS_117525875");
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam("{\"code\":\"123\"}");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//请求成功
				System.out.println("OK");
			} else {
				System.out.println(sendSmsResponse.getMessage());
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
