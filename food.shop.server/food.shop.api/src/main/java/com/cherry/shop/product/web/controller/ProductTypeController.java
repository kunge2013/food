package com.cherry.shop.product.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.utils.Results;
import com.cherry.shop.product.entity.ProductType;
import com.cherry.shop.product.param.api.ListProductTypeParam;
import com.cherry.shop.product.service.ProductTypeService;

/**
 * 产品类型控制器
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@RestController
@RequestMapping("/productType")
public class ProductTypeController {

	private static Logger logger = LoggerFactory.getLogger(ProductTypeController.class);
	
	@Autowired
	private ProductTypeService productTypeService;
	
	/**
	 * 查询产品分类列表
	 * 
	 * @param params
	 *            请求参数
	 * @return 产品分类列表
	 */
	@RequestMapping(path = "/list", method = GET)
	public Result list(ListProductTypeParam params) {
		try {
			List<ProductType> types = productTypeService.listProductTypeByParentId(params.getParentId());
			return Results.listResult(types);
		} catch (Exception e) {
			logger.error("查询产品分类失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
}
