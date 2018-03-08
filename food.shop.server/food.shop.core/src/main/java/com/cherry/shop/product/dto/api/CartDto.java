package com.cherry.shop.product.dto.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * 购物车Dto
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@Data
public class CartDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6055153799314541277L;

	private String stopTime;// 停止下单时间 HH:mm
	
	private String serviceTime;// 收取服务费时间 HH:mm
	
	private BigDecimal serviceFee;// 服务费
	
	private BigDecimal totalAmount;// 总金额
	
	private BigDecimal itemTotalAmount;// 产品总金额
	
	private BigDecimal minFee;// 最小费用，不足必须凑单
	
	private List<CartItemDto> items;// 购物车产品列表
	
}
