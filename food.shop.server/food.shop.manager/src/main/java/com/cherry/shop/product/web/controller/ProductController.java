package com.cherry.shop.product.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.product.entity.Product;
import com.cherry.shop.product.param.AddProductParam;
import com.cherry.shop.product.param.ListProductParam;
import com.cherry.shop.product.param.UpdateProductParam;
import com.cherry.shop.product.service.ProductService;
import com.cherry.shop.product.service.ProductTypeService;

/**
 * 产品控制器
 * 
 * @author 赵凡
 * @since 2018年1月12日
 * @version 1.0
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 显示产品页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("product/productGrid");
		return mav;
	}
	
	/**
	 * 查询产品列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 产品列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Page<Product> page, ListProductParam params) {
		try {
			return Results.pageResult(productService.listProduct(page, params));
		} catch (Exception e) {
			logger.error("查询产品列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询产品
	 * 
	 * @param params
	 *            查询参数
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result get(@Valid GetParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			return Results.queryOk(productService.getProduct(params.getId()));
		} catch (Exception e) {
			logger.error("查询产品失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询产品分类列表
	 * 
	 * @return 产品分类列表
	 */
	@RequestMapping("/listProductType")
	@ResponseBody
	public Result listProductType() {
		try {
			return Results.listResult(productTypeService.listLeafType());
		} catch (Exception e) {
			logger.error("查询产品分类失败！", e);
			return Results.queryError();
		}
	}
	
	/**
	 * 上传产品图片
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_PRODUCT);
	}
	
	/**
	 * 添加产品
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddProductParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productService.addProduct(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加产品失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新产品
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateProductParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productService.updateProduct(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新产品失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除产品
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 删除结果
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@Valid @RequestBody BatchDeleteParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productService.deleteProducts(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除产品失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
}
