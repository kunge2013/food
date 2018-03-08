package com.cherry.shop.product.param;

import com.cherry.framework.param.QueryOrderParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询产品分类列表请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListProductTypeParam extends QueryOrderParam {

	private String parentId;// 父类ID
	
	private Boolean show;// 是否显示
	
	private Boolean promotion;// 是否促销
	
}
