package com.cherry.shop.content.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告实体类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Notice extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4050871208947529829L;
	
	private String content;// 公告内容
	
	private Date startDate;// 开始日期
	
	private Date endDate;// 结束日期
	
	private String status;// 状态：Y、激活；N：禁用
	
	@TableField("`order`")
	private Integer order;// 顺序
	
	private Date createTime;// 创建时间
	
}
