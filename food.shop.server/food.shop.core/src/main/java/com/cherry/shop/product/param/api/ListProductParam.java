package com.cherry.shop.product.param.api;

import javax.validation.constraints.NotNull;

import com.cherry.framework.param.QueryPageOrderParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询产品列表请求参数
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListProductParam extends QueryPageOrderParam {

	@NotNull
	private String typeId;// 分类ID
	
	@NotNull
	private boolean promotion;// 只看促销
	
	private String search;// 搜索文本
	
}
