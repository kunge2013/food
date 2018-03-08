package com.cherry.shop.product.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 添加产品请求参数
 * 
 * @author 赵凡
 * @since 2018年1月13日
 * @version 1.0
 */
@Data
public class AddProductParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3675492436776470314L;

	@NotNull
	private String name;// 产品名称
	
	@NotNull
	@Size(min = 1)
	private String[] imageUrls;// 产品图片地址列表
	
	@NotNull
	private String intro;// 产品介绍
	
	@NotNull
	private String type;// 产品分类ID
	
	private boolean sale;// 是否在售
	
	private boolean promotion;// 是否促销
	
	@NotNull
	private Integer order;// 顺序
	
	@Valid
	@Size(min = 1)
	private List<SaveProductSpecParam> specs;// 产品规格
	
	@Valid
	@Size(min = 1)
	private List<SaveProductSpecParamsParam> specParams;// 产品规格参数
	
}
