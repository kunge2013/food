package com.cherry.shop.product.param.api;

import javax.validation.constraints.NotNull;

import com.cherry.framework.param.QueryPageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询产品单价请求参数
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListProductPriceParam extends QueryPageParam {

	@NotNull
	private String typeId;// 分类ID

}
