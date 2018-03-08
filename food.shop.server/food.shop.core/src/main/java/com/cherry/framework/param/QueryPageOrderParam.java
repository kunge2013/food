package com.cherry.framework.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页排序查询请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryPageOrderParam extends QueryPageParam {

	@NotNull
	private String orderByField;// 排序字段
	
	@NotNull
	private boolean asc;// 是否升序
	
}
