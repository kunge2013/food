package com.cherry.shop.security.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.dto.Result;
import com.cherry.framework.param.BatchDeleteParam;
import com.cherry.framework.param.GetParam;
import com.cherry.framework.param.ToggleStatusParam;
import com.cherry.framework.service.FileService;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.security.dto.SysUserDto;
import com.cherry.shop.security.dto.SysUserMenuItemDto;
import com.cherry.shop.security.dto.SysUserRolesDto;
import com.cherry.shop.security.entity.SysUser;
import com.cherry.shop.security.param.AddSysUserParam;
import com.cherry.shop.security.param.ChangePasswordParam;
import com.cherry.shop.security.param.GetMyMenuParam;
import com.cherry.shop.security.param.ListSysUserParam;
import com.cherry.shop.security.param.ResetPasswordParam;
import com.cherry.shop.security.param.SetSysUserRolesParam;
import com.cherry.shop.security.param.UpdateSysUserParam;
import com.cherry.shop.security.service.SysUserService;

/**
 * 系统用户
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
	
	private final static Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 显示系统用户页面
	 * 
	 * @param mav
	 *            ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping("/grid")
	public ModelAndView grid(ModelAndView mav) {
		mav.setViewName("security/sysUserGrid");
		return mav;
	}
	
	/**
	 * 查询系统用户列表
	 * 
	 * @param page
	 *            分页参数
	 * @param params
	 *            查询参数
	 * @return 系统用户列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list(Page<SysUser> page, ListSysUserParam params) {
		try {
			// 查询系统用户列表
			Page<SysUserDto> dtoPage = sysUserService.listSysUser(page, params);
			// 返回Page结果集
			return Results.pageResult(dtoPage);
		} catch (Exception e) {
			logger.error("查询系统用户列表失败！page:" + page.toString() + ",params:" + params.toString(), e);
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
	public Result get(@Valid GetParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			// 查询系统用户
			SysUserDto dto = sysUserService.getSysUser(params.getId());
			// 返回Page结果集
			return Results.queryOk(dto);
		} catch (Exception e) {
			logger.error("查询系统用户失败！params:" + params.toString(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 查询系统用户个人信息
	 * 
	 * @return 查询结果
	 */
	@RequestMapping("/getMyInfo")
	@ResponseBody
	public Result getMyInfo() {
		try {
			// 查询系统用户
			SysUserDto dto = sysUserService.getSysUser(ShiroUtils.getLoginSysUserId());
			// 返回Page结果集
			return Results.queryOk(dto);
		} catch (Exception e) {
			logger.error("查询系统用户个人信息失败！SysUserId:" + ShiroUtils.getLoginSysUserId(), e);
			return Results.queryError();
		}
	}
	
	/**
	 * 上传头像
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	@ResponseBody
	public Result uploadImage(MultipartFile file) {
		return fileService.uploadFile(file, Constants.FILE_DIR_SYSUSER);
	}
	
	/**
	 * 添加系统用户
	 * 
	 * @param params
	 *            添加系统用户请求参数
	 * @param r
	 *            参数验证结果
	 * @return 添加结果
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@Valid @RequestBody AddSysUserParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.addSysUser(params);
			
			return Results.addOk();
		} catch (Exception e) {
			logger.error("添加系统用户失败！params:" + params.toString(), e);
			return Results.addError();
		}
	}
	
	/**
	 * 更新系统用户
	 * 
	 * @param params
	 *            更新系统用户请求参数
	 * @param r
	 *            参数验证结果
	 * @return 更新结果
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@Valid @RequestBody UpdateSysUserParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.updateSysUser(params);
			
			return Results.updateOk();
		} catch (Exception e) {
			logger.error("更新系统用户失败！params:" + params.toString(), e);
			return Results.updateError();
		}
	}
	
	/**
	 * 批量删除系统用户
	 * 
	 * @param params
	 *            删除系统用户请求参数
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
			
			sysUserService.deleteSysUserByIds(params.getIds());
			
			return Results.delOk();
		} catch (Exception e) {
			logger.error("删除系统用户失败！params:" + params.toString(), e);
			return Results.delError();
		}
	}
	
	/**
	 * 切换系统用户状态
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            参数验证结果
	 * @return 切换状态结果
	 */
	@RequestMapping("/toggleStatus")
	@ResponseBody
	public Result toggleStatus(@Valid @RequestBody ToggleStatusParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.toggleStatus(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("删除系统用户失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 获取系统用户角色信息
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 系统用户角色信息
	 */
	@RequestMapping("/getSysUserRoles")
	@ResponseBody
	public Result getSysUserRoles(@Valid GetParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			SysUserRolesDto dto = sysUserService.getSysUserRoles(params.getId());
			
			return Results.queryOk(dto);
		} catch (Exception e) {
			logger.error("查询系统用户角色信息失败！params:" + params.toString(), e);
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
	@RequestMapping("/setSysUserRoles")
	@ResponseBody
	public Result setSysUserRoles(@Valid @RequestBody SetSysUserRolesParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.setSysUserRoles(params);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置系统用户角色信息失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 修改个人密码
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	public Result changePassword(@Valid @RequestBody ChangePasswordParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.changePassword(params.getPassword());
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("修改系统用户密码信息失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 重置系统用户密码
	 * 
	 * @param params 请求参数
	 * @param r 验证结果
	 * @return 请求结果
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public Result resetPassword(@Valid @RequestBody ResetPasswordParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			sysUserService.resetPassword(params.getSysUserId());
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("重置系统用户密码失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 获取我的菜单
	 * 
	 * @param params
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getMyMenu")
	@ResponseBody
	public Result getMyMenu(@Valid GetMyMenuParam params, BindingResult r) {
		try {
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 获取菜单会话属性
			Object obj = ShiroUtils.getSessionAttr(Constants.SYS_MENU_KEY);
			List<SysUserMenuItemDto> menus = null;
			if (obj == null) {// 没有会话属性
				// 从数据库加载
				menus = sysUserService.getMyMenuItems(params.getPlatform());
				// 设置会话属性
				ShiroUtils.setSessionAttr(Constants.SYS_MENU_KEY, menus);
			} else {
				// 有会话属性，直接使用会话属性
				menus = (ArrayList<SysUserMenuItemDto>) obj;
			}
			
			return Results.listResult(menus);
		} catch (Exception e) {
			logger.error("获取我的菜单失败！params:" + params.toString(), e);
			return Results.opError();
		}
	}
	
}
