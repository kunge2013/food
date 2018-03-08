package com.cherry.shop.product.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Results;
import com.cherry.shop.product.param.api.AddItemToCartParam;
import com.cherry.shop.product.service.CartService;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.service.UserService;

/**
 * 购物车控制器
 * 
 * @author 赵凡
 * @since 2018年1月21日
 * @version 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询我的购物车列表
	 * 
	 * @return 我的购物车列表
	 */
	@RequestMapping(path = "/list", method = GET)
	public Result list() {
		try {
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			User user = userService.getCurUser();
			return Results.listResult(cartService.listMyCart(user));
		} catch (Exception e) {
			logger.error("查询购物车条目列表失败！", e);
			return Results.queryError();
		}
	}
	
	/**
	 * 添加产品到购物车
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            参数验证结果
	 * @return 请求结果
	 */
	@RequestMapping(path = "/add", method = POST)
	public Result add(@Valid AddItemToCartParam param, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return cartService.addItem(param);
		} catch (Exception e) {
			logger.error("添加条目到购物车失败！param:" + param.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 从购物车批量删除条目
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 删除结果
	 */
	@RequestMapping(path = "/delete", method = POST)
	public Result delete(@Valid BatchDeleteParam param, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return cartService.deleteItem(param.getIds());
		} catch (Exception e) {
			logger.error("从购物车删除条目失败！param:" + param.toString(), e);
			return Results.delError();
		}
	}
	
}
