package com.cherry.framework.shiro.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cherry.framework.utils.SpringIoC;

/**
 * 安全相关工具类
 * 
 * @author 赵凡
 * @since 2017年11月13日
 * @version 1.0
 */
public class EncryptUtils {
	
	private static final HashConfig cfg = SpringIoC.getBean(HashConfig.class);
	
	/**
	 * 哈希配置
	 * 
	 * @author 赵凡
	 * @since 2017年11月13日
	 * @version 1.0
	 */
	@Component
	static class HashConfig {
		
		@Value("${shiro.hash.algorithmName}")
		String algorithmName;
		
		@Value("${shiro.hash.iterations}")
		Integer hashIterations;
		
	}
	
	/**
	 * 加密
	 * 
	 * @param encryptable
	 *            可加密的对象
	 */
	public static void encrypt(Encryptable encryptable) {
		RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		String salt = saltGenerator.nextBytes().toBase64();
		encrypt(encryptable, salt);
	}
	
	/**
	 * 加密
	 * 
	 * @param encryptable
	 *            可加密的对象
	 * @param salt
	 *            盐
	 */
	public static void encrypt(Encryptable encryptable, String salt) {
		encryptable.setSalt(salt);
		encryptable.setCipherText(encrypt(encryptable.getPlainText(), salt));
	}
	
	/**
	 * 加密
	 * 
	 * @param plainText
	 *            原文
	 * @param salt
	 *            盐
	 * @return 密文
	 */
	public static String encrypt(String plainText, String salt) {
		SimpleHash hash = new SimpleHash(cfg.algorithmName, plainText, salt, cfg.hashIterations);
		return hash.toString();
	}
	
}