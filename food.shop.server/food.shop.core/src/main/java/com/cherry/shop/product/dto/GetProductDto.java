package com.cherry.shop.product.dto;

import java.io.Serializable;
import java.util.List;

import com.cherry.shop.product.entity.Product;
import com.cherry.shop.product.entity.ProductType;

import lombok.Data;

/**
 * 获取产品Dto
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Data
public class GetProductDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1125165285051136512L;
	
	private Product product;// 产品信息
	
	private List<ProductType> productTypes;// 产品分类
	
}
