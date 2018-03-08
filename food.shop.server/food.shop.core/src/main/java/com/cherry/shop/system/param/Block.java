package com.cherry.shop.system.param;

/**
 * 配置块
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
public enum Block {

	/**
	 * 公司信息配置块
	 * 
	 */
	COMPANY_INFO("content.company"),
	/**
	 * 购物车配置块
	 * 
	 */
	CART("cart");
	
	private String block;// 配置块
	
	/**
	 * 获取配置块
	 * 
	 * @return 配置块
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * 设置配置快
	 * 
	 * @param block
	 *            配置快
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * 创建配置块
	 * 
	 * @param block
	 *            配置块
	 */
	private Block(String block) {
		this.block = block;
	}
	
}
