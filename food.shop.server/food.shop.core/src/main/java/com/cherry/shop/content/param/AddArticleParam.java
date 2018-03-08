package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddArticleParam {

	
	private String icon; //文章图标
	@NotNull
	private String title ;//文章标题
	@NotNull
	private String content ;//文章内容
}
