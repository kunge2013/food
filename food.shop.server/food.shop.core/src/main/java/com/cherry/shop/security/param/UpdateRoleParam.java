package com.cherry.shop.security.param;

import lombok.Data;

/**
 * 更新角色请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class UpdateRoleParam {

	private String id;// 角色ID
	
	private String name;// 角色名称
	
}
