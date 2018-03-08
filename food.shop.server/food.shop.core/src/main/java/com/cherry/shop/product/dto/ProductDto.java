package com.cherry.shop.product.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cherry.shop.product.entity.ProductSpec;
import com.cherry.shop.product.entity.ProductSpecParam;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 产品Dto
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Data
public class ProductDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4373074045421803398L;

	private String id;// 产品ID
	
	private String name;// 产品名称
	
	private String imageUrl;// 产品图片地址
	
	private String[] imageUrls;// 产品图片地址列表
	
	private String intro;// 产品介绍
	
	private String type;// 产品分类ID
	
	private String typeName;// 产品分类名称
	
	private boolean sale;// 是否在售
	
	private boolean promotion;// 是否促销
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间

	private Integer order;// 顺序
	
	private Integer salesVolume;// 销售量
	
	private List<ProductSpec> specs;// 规格
	
	private List<ProductSpecParam> params;// 规格参数
	
}
