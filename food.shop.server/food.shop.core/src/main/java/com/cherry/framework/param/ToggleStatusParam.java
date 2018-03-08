package com.cherry.framework.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 切换状态请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class ToggleStatusParam {

	@NotNull
	private String id;// ID
	
	@NotNull
	private String status;// 状态：Y、激活；N、禁用
	
}
