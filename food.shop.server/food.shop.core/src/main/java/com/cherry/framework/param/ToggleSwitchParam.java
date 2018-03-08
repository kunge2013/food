package com.cherry.framework.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 切换开关请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class ToggleSwitchParam {

	@NotNull
	private String id;// ID
	
	@NotNull
	private Boolean open;// 开关，是否打开
	
}
