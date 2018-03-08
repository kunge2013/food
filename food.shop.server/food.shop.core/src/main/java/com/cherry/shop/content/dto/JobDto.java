package com.cherry.shop.content.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 
 * @author 崔力
 *
 */
@Data
public class JobDto implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String id; //主键
	private String position; //职位
	private String responsibilities ;//工作职责
	private String requirements ;//任职要求
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishTime;//发布时间

	private Boolean show ;//是否显示：Y(1)、显示；N(0)：隐藏'
	
}
