package com.cherry.shop.content.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.cherry.framework.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListFagParam extends BaseEntity{

	
	private static final long serialVersionUID = 5173506704243100860L;
	
	
	private String question ; //问题
	private String answer ;//答案
	private boolean asc;// 按照创建时间升序排列
	

	
}
