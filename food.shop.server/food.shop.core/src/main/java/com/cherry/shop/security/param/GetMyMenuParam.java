package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 获取我的菜单请求参数
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Data
public class GetMyMenuParam {

	@NotNull
	private String platform;// 平台
	
}
