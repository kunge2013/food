package com.cherry.shop.content.param;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.cherry.framework.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddFagParam extends BaseEntity{

	
	private static final long serialVersionUID = 5173506704243100860L;
	
	@NotNull
	private String question ; //问题
	@NotNull
	private String answer ;//答案
	
	

	
}
