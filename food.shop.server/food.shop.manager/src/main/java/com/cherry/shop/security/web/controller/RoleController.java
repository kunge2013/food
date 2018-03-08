package com.cherry.shop.security.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.utils.Results;
import com.cherry.shop.security.dto.RolePermissionsDto;
import com.cherry.shop.security.entity.Role;
import com.cherry.shop.security.param.AddRoleParam;
import com.cherry.shop.security.param.ListRoleParam;
import com.cherry.shop.security.param.SetRolePermissionsParam;
import com.cherry.shop.security.param.UpdateRoleParam;
import com.cherry.shop.security.service.RoleService;

/**
 * 角色控制器
 * 
 * @author 赵凡
 * @since 2018年1月4日
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 显示角色页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("security/roleGrid");
		return mav;
	}
	
	/**
	 * 查询角色列表
	 * 
	 * @param params
	 *            查询参数
	 * @return 角色列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(ListRoleParam params) {
		try {
			// 查询角色列表
			List<Role> dtoList = roleService.listRole(params);
			// 返回Page结果集
			return Results.listResult(dtoList);
		} catch (Exception e) {
			logger.error("查询角色列表失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询系统用户
	 * 
	 * @param params
	 *            查询参数
	 * @return 查询结果
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Result get(GetParam params) {
		try {
			// 查询角色
			Role role = roleService.getRole(params.getId());
			// 返回Page结果集
			return Results.queryOk(role);
		} catch (Exception e) {
			logger.error("查询角色失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 添加角色
	 * 
	 * @param params
	 *            添加角色请求参数
	 * @param r
	 *            参数验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddRoleParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			roleService.addRole(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加角色失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新角色
	 * 
	 * @param params
	 *            更新角色请求参数
	 * @param r
	 *            参数验证结果
	 * @return 更新结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateRoleParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			roleService.updateRole(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新角色失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除角色
	 * 
	 * @param params
	 *            删除角色请求参数
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
			
			roleService.deleteRoleByIds(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除角色失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 获取角色的权限信息
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 角色的权限信息
	 */
	@RequestMapping("/getRolePermissions")
	@ResponseBody
	public Result getRolePermissions(@Valid GetParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			RolePermissionsDto dto = roleService.getRolePermissions(params.getId());
			
			return Results.queryOk(dto);
		} catch (Exception e) {
			logger.error("查询角色的功能权限信息失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 为系统用户设置角色
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证参数
	 * @return 设置结果
	 */
	@RequestMapping("/setRolePermissions")
	@ResponseBody
	public Result setRolePermissions(@Valid @RequestBody SetRolePermissionsParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			roleService.setRolePermissions(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置角色的功能权限信息失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
}
