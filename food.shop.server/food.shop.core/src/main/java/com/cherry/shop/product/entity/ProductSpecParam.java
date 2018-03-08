package com.cherry.shop.product.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品规格参数实体类
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductSpecParam extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4379028760389442332L;

	private String productSpecId;// 产品规格ID
	
	private String productId;// 产品ID
	
	private String paramName;// 参数名
	
	private String paramValue;// 参数值
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
}
