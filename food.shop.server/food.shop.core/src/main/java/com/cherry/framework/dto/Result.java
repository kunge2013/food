package com.cherry.framework.dto;

import java.io.Serializable;

/**
 * 响应结果DTO
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
public class Result {

	private String code;// 响应码，0表示成功，其它表示失败
	
	private String message;// 响应消息
	
	private boolean success;// 是否成功
	
	private Serializable data;// 响应数据
	
	/**
	 * 创建响应结果DTO
	 * 
	 */
	public Result() {}
	
	/**
	 * 创建响应结果DTO
	 * 
	 * @param code
	 *            响应码，0表示成功，其它表示失败
	 * @param message
	 *            响应消息
	 * @param success
	 *            是否成功
	 * @param data
	 *            响应数据
	 */
	public Result(String code, String message, boolean success, Serializable data) {
		this.code = code;
		this.message = message;
		this.success = success;
		this.data = data;
	}

	/**
	 * 获取响应码
	 * 
	 * @return 响应码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置响应码
	 * 
	 * @param code
	 *            响应码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取响应消息
	 * 
	 * @return 响应消息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置响应消息
	 * 
	 * @param message
	 *            响应消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 判断响应是否成功
	 * 
	 * @return 响应是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置响应成功状态
	 * 
	 * @param success
	 *            响应成功状态
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 获取响应数据
	 * 
	 * @return 响应数据
	 */
	public Serializable getData() {
		return data;
	}

	/**
	 * 设置响应数据
	 * 
	 * @param data
	 *            响应数据
	 */
	public void setData(Serializable data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", success=" + success + ", data=" + data + "]";
	}
	
}
