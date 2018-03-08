package com.cherry.shop.product.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.product.param.api.ListProductParam;
import com.cherry.shop.product.param.api.ListProductPriceParam;
import com.cherry.shop.product.service.ProductService;

/**
 * 产品控制器
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class); 
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 查询产品当前价格列表
	 * 
	 * @param params
	 *            查询参数
	 * @param r
	 *            验证参数
	 * @return 产品当前价格列表
	 */
	@RequestMapping(path = "/price/list", method = GET)
	public Result listPrice(@Valid ListProductPriceParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			return Results.pageResult(productService.listProductPrice(params));
		} catch (Exception e) {
			logger.error("查询产品当前价格列表失败！params：" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询产品列表
	 * 
	 * @param params
	 *            查询参数
	 * @param r
	 *            验证参数
	 * @return 产品列表
	 */
	@RequestMapping(path = "/list", method = GET)
	public Result list(@Valid ListProductParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			return Results.pageResult(productService.listProduct(params));
		} catch (Exception e) {
			logger.error("查询产品列表失败！params：" + params.toString(), e);
			return Results.queryError();
		}
	}
	
}
