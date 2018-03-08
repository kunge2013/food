package com.cherry.shop.security.param;

import lombok.Data;

/**
 * 查询角色列表请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class ListRoleParam {

	private String name;// 角色名称
	
	private boolean asc;// 按照创建时间升序排列
	
}
