package com.cherry.shop.content.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 公告DTO 
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class NoticeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3613271180281729596L;

	private String id;// 公告ID
	
	private String content;// 公告内容
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate;// 开始日期
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;// 结束日期
	
	private String status;// 状态：Y、激活；N：禁用
	
	private boolean active;// 是否激活
	
	private Integer order;// 顺序
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	
}
