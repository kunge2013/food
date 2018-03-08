package com.cherry.shop.security.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加角色请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class AddRoleParam {

	@NotNull
	private String name;// 角色名称
	
}
