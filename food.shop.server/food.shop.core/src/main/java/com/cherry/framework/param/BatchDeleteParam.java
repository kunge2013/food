package com.cherry.framework.param;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 批量删除请求参数
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Data
public class BatchDeleteParam {

	@NotNull
	@Size(min = 1)
	private ArrayList<String> ids;// ID列表
	
}
