package com.cherry.shop.product.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车实体类
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Cart extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5286712783202322353L;

	private String productId;// 产品ID
	
	private String productName;// 产品名称
	
	private String productImage;// 产品图片
	
	private String specId;// 规格ID
	
	private String unit;// 规格单位
	
	private BigDecimal price;// 规格单价
	
	private Integer quantity;// 规格数量
	
	private BigDecimal amount;// 总金额
	
	private String measureUnit;// 计量单位
	
	private BigDecimal measurePrice;// 计量单价
	
	private String companyId;// 企业ID
	
	private String creatorId;// 添加者
	
	private Date createTime;// 创建时间
	
}
