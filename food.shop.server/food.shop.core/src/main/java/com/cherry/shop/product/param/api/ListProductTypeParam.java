package com.cherry.shop.product.param.api;

import lombok.Data;

/**
 * 查询产品类型列表接口请求参数
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
public class ListProductTypeParam {

	private String parentId;
	
}
