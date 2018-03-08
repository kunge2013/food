package com.cherry.shop.system.param;

/**
 * 配置键
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
public enum ConfigName {

	/**
	 * 公司介绍信息
	 * 
	 */
	COMPANY_CONTENT_CONFIG_NAME("company.content"),
	/**
	 * 公司介绍图片
	 * 
	 */
	COMPANY_IMAGE_CONFIG_NAME("company.image"),
	/**
	 * 企业介绍信息
	 * 
	 */
	ENTERPRISE_CONTENT_CONFIG_NAME("enterprise.content"),
	/**
	 * 企业介绍图片
	 * 
	 */
	ENTERPRISE_IMAGE_CONFIG_NAME("enterprise.image"),
	/**
	 * 停止下单时间
	 * 
	 */
	STOP_TIME("stop.time"),
	/**
	 * 收取服务费时间
	 * 
	 */
	SERVICE_TIME("service.time"),
	/**
	 * 收取服务费用
	 * 
	 */
	SERVICE_FEE("service.fee"), 
	/**
	 * 最小订单总价
	 * 
	 */
	MIN_ORDER_AMOUNT("min.order.amount");
	
	private String key;// 配置键
	
	/**
	 * 获取配置键
	 * 
	 * @return 配置键
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置配置键
	 * 
	 * @param key
	 *            配置键
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 创建配置块
	 * 
	 * @param key
	 *            配置块
	 */
	private ConfigName(String key) {
		this.key = key;
	}
	
}
