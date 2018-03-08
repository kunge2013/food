package com.cherry.shop.product.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 产品分类DTO
 * 
 * @author 赵凡
 * @since 2018年1月11日
 * @version 1.0
 */
@Data
public class ProductTypeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4682502688587275802L;

	private String id;// 产品分类ID

	private String name;// 产品类型名称
	
	private String parentId;// 父类型ID
	
	private String parentName;// 父类名称
	
	private String imageUrl;// 产品类型图标
	
	private boolean show;// 是否显示
	
	private boolean promotion;// 是否促销

	private Integer order;// 顺序
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间
	
}
