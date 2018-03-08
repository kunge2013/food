package com.cherry.shop.product.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品规格实体类
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSpec extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7543710208542610424L;

	private String productId;// 产品ID
	
	private String type;// 产品分类
	
	private String unit;// 规格单位
	
	private BigDecimal price;// 规格单价
	
	private Integer stock;// 库存，规格数量
	
	private String measureUnit;// 计量单位
	
	private BigDecimal measurePrice;// 计量单价
	
	private Integer measureStock;// 计量数量
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间

	@TableField("is_sale")
	private boolean sale;// 是否销售
	
}
