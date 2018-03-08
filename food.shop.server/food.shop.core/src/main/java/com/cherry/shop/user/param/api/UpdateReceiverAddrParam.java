package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改收货地址请求参数
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@Data
public class UpdateReceiverAddrParam {

	@NotNull
	private String id;// 收货地址ID
	
	@NotNull
	private String receiverName;// 收货人姓名
	
	@NotNull
	private String telephone;// 手机号
	
	@NotNull
	private String region;// 所属地区
	
	@NotNull
	private String street;// 街道
	
	private boolean defaultAddr;// 默认地址
	
}
