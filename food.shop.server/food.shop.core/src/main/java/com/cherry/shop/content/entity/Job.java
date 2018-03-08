package com.cherry.shop.content.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@EqualsAndHashCode(callSuper=false)
public class Job extends BaseEntity{

	
	private static final long serialVersionUID = 5173506704243100860L;
	
	private String id; //主键
	private String position; //职位
	private String responsibilities ;//工作职责
	private String requirements ;//任职要求
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishTime;//发布时间
	
	private Boolean show;// 是否显示 ;//是否显示：Y(1)、显示；N(0)：隐藏'
	
}
