package com.cherry.framework.dto;

/**
 * 选项DTO
 * 
 * @author 赵凡
 * @since 2018年1月5日
 * @version 1.0
 */
public class OptionDto {

	private String value;// 值，一般对应ID
	
	private String label;// 标签，一般对应名称
	
	private boolean disabled;// 是否禁用

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 设置标签
	 * 
	 * @param label
	 *            标签
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 是否禁用
	 * 
	 * @return 是否禁用
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * 设置是否禁用
	 * 
	 * @param disabled
	 *            是否禁用
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}
