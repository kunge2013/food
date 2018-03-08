package com.cherry.shop.content.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Carousel  extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597039708798823725L;
	
	private String type;// 轮播图类型：C、普通轮播图；L、链接轮播图
	
	private String url;// 轮播图地址
	
	private String targetUrl;// 目标地址
	
	@TableField("`order`")
	private Integer order ;// 顺序
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
	private String platform;// 平台
	
}
