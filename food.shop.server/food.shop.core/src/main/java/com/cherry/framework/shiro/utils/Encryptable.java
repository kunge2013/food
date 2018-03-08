package com.cherry.framework.shiro.utils;

/**
 * 可加密接口
 * 
 * @author 赵凡
 * @since 2017年11月13日
 * @version 1.0
 */
public interface Encryptable {

	/**
	 * 获取明文
	 * 
	 * @return 明文
	 */
	String getPlainText();
	
	/**
	 * 设置密文
	 * 
	 * @param cipherText
	 *            密文
	 */
	void setCipherText(String cipherText);
	
	/**
	 * 获取盐
	 * 
	 * @return 盐
	 */
	String getSalt();
	
	/**
	 * 设置盐
	 * 
	 * @param salt
	 *            盐
	 */
	void setSalt(String salt);
	
}