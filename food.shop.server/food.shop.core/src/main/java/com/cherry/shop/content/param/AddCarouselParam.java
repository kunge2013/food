package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加轮播图请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class AddCarouselParam {

	@NotNull
	private String type;// 轮播图类型
	
	@NotNull
	private String platform;// 平台
	
	@NotNull
	private String url;// 轮播图地址
	
	private String targetUrl;// 目标地址
	
	@NotNull
	private Integer order;// 顺序
	
}
