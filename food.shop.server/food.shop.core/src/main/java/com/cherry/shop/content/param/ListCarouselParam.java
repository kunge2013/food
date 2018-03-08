package com.cherry.shop.content.param;

import com.cherry.framework.param.QueryParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 鏌ヨ杞挱鍥惧垪琛ㄨ姹傚弬鏁�
 * 
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListCarouselParam extends QueryParam {

	private String platform;// 骞冲彴
	
	private String type;// 绫诲瀷
	
}
