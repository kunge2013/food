package com.cherry.shop.user.param.api;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加并绑定企业请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
public class AddCompanyParam {

	@NotNull
	private String name;// 企业名称
	
}
