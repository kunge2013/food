package com.cherry.shop.content.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.cherry.framework.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@EqualsAndHashCode(callSuper=false)
public class Article extends BaseEntity{

	
	private static final long serialVersionUID = 5173506704243100860L;
	
	private String id; //主键
	private String icon; //文章图标
	private String title ;//文章标题
	private String content ;//文章内容
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//创建时间
	
	
}
