package com.cherry.shop.content.param;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 添加公告请求参数
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Data
public class AddNoticeParam {

	@NotNull
	private String content;// 公告内容
	
	private Date startDate;// 开始时间
	
	private Date endDate;// 结束时间
	
	@NotNull
	private Integer order;// 顺序
	
}
