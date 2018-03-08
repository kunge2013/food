package com.cherry.shop.content.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotations.TableName;
import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("faq")
public class Fag extends BaseEntity{

	
	private static final long serialVersionUID = 5173506704243100860L;
	
	private String id; //主键
	private String question ; //问题
	private String answer ;//答案
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime ;//创建时间
	

	
}
