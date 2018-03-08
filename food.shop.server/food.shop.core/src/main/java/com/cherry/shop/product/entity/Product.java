package com.cherry.shop.product.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品实体类
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6112283526305877517L;

	private String name;// 产品名称
	
	private String imageUrl;// 产品图片地址
	
	private String intro;// 产品介绍
	
	private String type;// 产品分类ID
	
	@TableField("is_sale")
	private boolean sale;// 是否在售
	
	@TableField("is_promotion")
	private boolean promotion;// 是否促销
	
	private Date createTime;// 创建时间

	@TableField("`order`")
	private Integer order;// 顺序
	
	private Integer salesVolume;// 销售量
	
}
