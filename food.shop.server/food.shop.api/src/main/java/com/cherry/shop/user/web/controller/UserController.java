package com.cherry.shop.user.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cherry.framework.dto.Result;
import com.cherry.framework.service.FileService;
import com.cherry.framework.service.MessageService;
import com.cherry.framework.shiro.cache.CacheService;
import com.cherry.framework.shiro.utils.ShiroUtils;
import com.cherry.framework.utils.Constants;
import com.cherry.framework.utils.Results;
import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.param.api.GetTelephoneCodeParam;
import com.cherry.shop.user.param.api.RegistTelephoneParam;
import com.cherry.shop.user.param.api.SetBirthdayParam;
import com.cherry.shop.user.param.api.SetNickNameParam;
import com.cherry.shop.user.param.api.SetSexParam;
import com.cherry.shop.user.param.api.TelephoneFindPasswordParam;
import com.cherry.shop.user.service.UserService;

/**
 * 用户控制器
 * 
 * @author 赵凡
 * @since 2018年1月17日
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 获取短信验证码
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结构
	 * @return 请求结果
	 */
	@RequestMapping(path = "/get/telephone/code", method = GET)
	public Result getTelephoneCode(@Valid GetTelephoneCodeParam param, BindingResult r) {
		// 验证失败
		if (r.hasErrors()) {
			return Results.paramError(r);
		}
		
		try {
			// 缓存验证码
			String code = RandomStringUtils.randomNumeric(6);
			cacheService.set(Constants.CAPTCHA_CODE_PREFIX + param.getTelephone(), code, 60);
			
			messageService.sendCaptchaSms(code, param.getTelephone());
			return Results.opOk();
		} catch (Exception e) {
			logger.error("获取短信验证码失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}

	/**
	 * 用户注册接口-手机注册
	 * 
	 * @param param
	 *            注册参数
	 * @param r
	 *            验证结果
	 * @return 注册结果
	 */
	@RequestMapping(path = "/regist/telephone", method = POST)
	public Result registTelephone(@Valid RegistTelephoneParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 获取短信验证码
			String code = (String) cacheService.get(Constants.CAPTCHA_CODE_PREFIX + param.getTelephone());
			if (StringUtils.isBlank(code)) {// 验证码失效
				return Results.codeInvalidError();
			} else if (!code.equalsIgnoreCase(param.getCode())) {
				return Results.codeError();
			}
					
			// 复制请求参数到实体类中
			User user = new User();
			BeanUtils.copyProperties(param, user);
			user.setUsername(user.getTelephone());
					
			// 注册用户
			return userService.insertUser(user);
		} catch (Exception e) {
			logger.error("注册用户失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 手机短信找回密码
	 * 
	 * @param param
	 *            注册参数
	 * @param r
	 *            验证结果
	 * @return 注册结果
	 */
	@RequestMapping(path = "/telephone/findPassword", method = POST)
	public Result findPassword_Telephone(@Valid TelephoneFindPasswordParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 获取短信验证码
			String code = (String) cacheService.get(Constants.CAPTCHA_CODE_PREFIX + param.getTelephone());
			if (StringUtils.isBlank(code)) {// 验证码失效
				return Results.codeInvalidError();
			} else if (!code.equalsIgnoreCase(param.getCode())) {
				return Results.codeError();
			}
					
			// 更新密码
			return userService.updatePasswordByTelephone(param.getTelephone(), param.getPassword());
		} catch (Exception e) {
			logger.error("找回密码失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 登录
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return 登录结果
	 */
	@RequestMapping("/login")
	public Result login(HttpServletRequest req) {
		// 获取登录异常类名
		String className = (String) req.getAttribute("shiroLoginFailure");
		
		if (StringUtils.isNotBlank(className)) {
			// 登录异常处理
			if (UnknownAccountException.class.getName().equals(className) || 
					IncorrectCredentialsException.class.getName().equals(className)) {
				return Results.usernameOrPasswordError();
			} else {
				return Results.error();
			}
		}
		
		return Results.opOk();
	}
	
	/**
	 * 获取个人信息
	 * 
	 * @param param
	 *            注册参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping(path = "/info/get", method = GET)
	public Result getInfo() {
		try {
			// 登录校验
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			return Results.queryOk(userService.getInfo());
		} catch (Exception e) {
			logger.error("获取个人信息失败！", e);
			return Results.queryError();
		}
	}
	
	/**
	 * 设置昵称
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping(path = "/info/set/nickName", method = POST)
	public Result setNickName(@Valid SetNickNameParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录校验
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			// 更新
			User user = new User();
			user.setId(ShiroUtils.getLoginUserId());
			user.setNickName(param.getNickName());
			userService.updateById(user);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置昵称失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 设置性别
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping(path = "/info/set/sex", method = POST)
	public Result setSex(@Valid SetSexParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录校验
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			// 更新
			User user = new User();
			user.setId(ShiroUtils.getLoginUserId());
			user.setSex(param.getSex());
			userService.updateById(user);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置性别失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}

	/**
	 * 设置生日
	 * 
	 * @param param
	 *            请求参数
	 * @param r
	 *            验证结果
	 * @return 请求结果
	 */
	@RequestMapping(path = "/info/set/birthday", method = POST)
	public Result setBirthday(@Valid SetBirthdayParam param, BindingResult r) {
		try {
			// 验证失败
			if (r.hasErrors()) {
				return Results.paramError(r);
			}
			
			// 登录校验
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			// 更新
			User user = new User();
			user.setId(ShiroUtils.getLoginUserId());
			user.setBirthday(param.getBirthday());
			userService.updateById(user);
			
			return Results.opOk();
		} catch (Exception e) {
			logger.error("设置生日失败！params:" + param.toString(), e);
			return Results.opError();
		}
	}
	
	/**
	 * 上传用户头像
	 * 
	 * @param file
	 *            MultipartFile
	 * @return 上传结果
	 */
	@RequestMapping("/uploadImage")
	public Result uploadImage(@RequestParam(required = true) MultipartFile file) {
		try {
			// 登录校验
			if (ShiroUtils.isNotLogin()) {
				return Results.notLoginError();
			}
			
			// 上传文件
			Result r = fileService.uploadFile(file, Constants.FILE_DIR_USER);
			
			// 更新头像
			User user = new User();
			user.setId(ShiroUtils.getLoginUserId());
			user.setImage(r.getData().toString());
			userService.updateById(user);
			
			return r;
		} catch (Exception e) {
			logger.error("上传用户头像失败！params:" + file.toString(), e);
			return Results.uploadError();
		}
	}
	
}
