package com.cherry.shop.content.param;

import lombok.Data;

@Data
public class ListArticleParam {

	private String title; //文章标题
/*	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//创建时间
*/	private boolean asc;// 按照创建时间升序排列
}
