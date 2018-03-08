package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddJobParam {

	@NotNull
	private String position;//职位
	@NotNull
	private String responsibilities ;//工作职责
	private String requirements ;//任职要求
	private Boolean show;// 是否显示 ;//是否显示：Y(1)、显示；N(0)：隐藏'
}
