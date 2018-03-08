package com.cherry.shop.content.param;

import lombok.Data;

@Data
public class ListJobParam {

	private String position; //职位
	private Boolean show;// 是否显示
	private boolean asc;// 按照创建时间升序排列
}
