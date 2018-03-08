package com.cherry.shop.system.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件实体类
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Atta extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5829283838009872466L;

	private String type;// 附件类型
	
	private String masterId;// 主件ID
	
	private String url;// URL地址
	
	private Date uploadTime;// 上传时间
	
}
