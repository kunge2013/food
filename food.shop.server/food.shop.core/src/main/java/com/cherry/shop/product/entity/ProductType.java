package com.cherry.shop.product.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品类型实体类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -200122410023885147L;

	private String name;// 产品类型名称
	
	private String parentId;// 父类型ID
	
	private String imageUrl;// 产品类型图标
	
	@TableField("`show`")
	private boolean show;// 是否显示
	
	private boolean promotion;// 是否促销

	@TableField("`order`")
	private Integer order;// 顺序
	
	private Date createTime;// 创建时间
	
}
