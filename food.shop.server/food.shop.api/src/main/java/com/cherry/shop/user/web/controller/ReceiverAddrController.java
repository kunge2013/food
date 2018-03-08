package com.cherry.shop.user.web.controller;

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
import com.cherry.framework.param.DeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Results;
import com.cherry.shop.user.param.api.AddReceiverAddrParam;
import com.cherry.shop.user.param.api.UpdateReceiverAddrParam;
import com.cherry.shop.user.service.ReceiverAddrService;

/**
 * 收货地址控制器
 * 
 * @author 赵凡
 * @since 2018年1月18日
 * @version 1.0
 */
@RestController
@RequestMapping("/receiverAddr")
public class ReceiverAddrController {

	private static final Logger logger = LoggerFactory.getLogger(ReceiverAddrController.class);
	
	@Autowired
	private ReceiverAddrService receiverAddrService;
	
	/**
	 * 查询我的收货地址列表
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 收货地址列表
	 */
	@RequestMapping(path = "/list", method = GET)
	public Result list() {
		try {
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return Results.listResult(receiverAddrService.listMyReceiverAddr());
		} catch (Exception e) {
			logger.error("查询收货地址列表失败！", e);
			return Results.queryError();
		}
	}
	
	/**
	 * 添加收货地址
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/add", method = POST)
	public Result add(@Valid AddReceiverAddrParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			receiverAddrService.addReceiverAddr(param);
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加收货地址失败！params:" + param.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 修改收货地址
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/update", method = POST)
	public Result update(@Valid UpdateReceiverAddrParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			receiverAddrService.updateReceiverAddr(param);
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("修改收货地址失败！params:" + param.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 获取收货地址
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/get", method = GET)
	public Result get(@Valid GetParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return Results.queryOk(receiverAddrService.getReceiverAddr(param.getId()));
		} catch (Exception e) {
			logger.error("获取收货地址失败！params:" + param.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 设置/取消收货地址
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/setDefault", method = POST)
	public Result setDefault(@Valid ToggleSwitchParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			receiverAddrService.toggleDefaultReceiverAddr(param);
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置/取消默认收货地址失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 删除收货地址
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/delete", method = POST)
	public Result delete(@Valid DeleteParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录认证
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			receiverAddrService.deleteById(param.getId());
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除收货地址失败！params:" + param.toString(), e);
			return Results.delError();
		}
	}
	
}
