package com.cherry.framework.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 删除请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class DeleteParam {

	@NotNull
	private String id;// 实体ID
	
}
