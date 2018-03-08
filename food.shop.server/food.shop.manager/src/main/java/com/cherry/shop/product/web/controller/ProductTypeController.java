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

import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.framework.service.FileService;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.product.param.AddProductTypeParam;
import com.cherry.shop.product.param.ListProductTypeParam;
import com.cherry.shop.product.param.UpdateProductTypeParam;
import com.cherry.shop.product.service.ProductTypeService;

/**
 * 产品分类控制器
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Controller
@RequestMapping("/productType")
public class ProductTypeController {

	private final static Logger logger = LoggerFactory.getLogger(ProductTypeController.class);
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 显示产品分类页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("product/productTypeGrid");
		return mav;
	}
	
	/**
	 * 查询产品分类列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 产品分类列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(ListProductTypeParam params) {
		try {
			return Results.listResult(productTypeService.listProductType(params));
		} catch (Exception e) {
			logger.error("查询产品分类列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询产品分类
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
			return Results.queryOk(productTypeService.getProductType(params.getId()));
		} catch (Exception e) {
			logger.error("查询产品分类失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 上传产品分类图标
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_PRODUCT_TYPE);
	}
	
	/**
	 * 添加产品分类
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddProductTypeParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productTypeService.addProductType(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加产品分类失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新产品分类
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateProductTypeParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productTypeService.updateProductType(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新产品分类失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除产品类型
	 * 
	 * @param params
	 *            删除产品类型请求参数
	 * @param r
	 *            参数验证结果
	 * @return 删除结果
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(@Valid @RequestBody BatchDeleteParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			productTypeService.deleteProductTypeByIds(params.getIds());
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除产品类型失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 切换产品分类显示状态
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            参数验证结果
	 * @return 切换状态结果
	 */
	@RequestMapping("/toggleShow")
	@ResponseBody
	public Result toggleShow(@Valid @RequestBody ToggleSwitchParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productTypeService.toggleShow(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("切换产品分类显示状态失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 切换产品分类促销状态
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            参数验证结果
	 * @return 切换状态结果
	 */
	@RequestMapping("/togglePromotion")
	@ResponseBody
	public Result togglePromotion(@Valid @RequestBody ToggleSwitchParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			productTypeService.togglePromotion(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("切换产品分类促销状态失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
}
