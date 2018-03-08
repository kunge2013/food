package com.cherry.framework.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.baomidou.mybatisplus.plugins.Page;
import com.cherry.framework.dto.QueryResult;
import com.cherry.framework.dto.Result;

/**
 * 响应结果工具类，用于创建响应结果
 * 
 * @author 赵凡
 * @since 2017年11月10日
 * @version 1.0
 */
public class Results {
	
	// ================================= 成功响应部分 ====================================

	/**
	 * 创建成功响应结果DTO
	 * 
	 * @param message
	 *            响应消息
	 * @param data
	 *            响应数据
	 * @return 成功响应结果DTO
	 */
	public static Result ok(String message, Serializable data) {
		if (data == null) {
			data = (Serializable) Collections.EMPTY_MAP;
		}
		if (data instanceof Page<?>) {
			return new QueryResult(Constants.SUCCESS_CODE, SpringIoC.getMessage(message), true, (Page<?>) data);
		} else if (data instanceof List<?>) {
			return new QueryResult(Constants.SUCCESS_CODE, SpringIoC.getMessage(message), true, (List<?>) data);
		}
		return new Result(Constants.SUCCESS_CODE, SpringIoC.getMessage(message), true, data);
	}
	
	/**
	 * 创建List结果集
	 * 
	 * @param list
	 *            java.util.List
	 * @return List结果集
	 */
	public static Result listResult(List<?> list) {
		return new QueryResult(Constants.SUCCESS_CODE, Constants.SUCCESS_QUERY_MESSAGE_KEY, true, list);
	}
	
	/**
	 * 创建MyBatis Plus的Page结果集
	 * 
	 * @param page
	 *            MyBatis Plus的Page
	 * @return MyBatis Plus的Page结果集
	 */
	public static Result pageResult(Page<?> page) {
		return new QueryResult(Constants.SUCCESS_CODE, Constants.SUCCESS_QUERY_MESSAGE_KEY, true, page);
	}
	
	/**
	 * 创建查询成功结果集
	 * 
	 * @param data
	 *            查询到的数据
	 * @return 查询成功结果集
	 */
	public static Result queryOk(Serializable data) {
		return ok(Constants.SUCCESS_QUERY_MESSAGE_KEY, data);
	}
	
	/**
	 * 创建一个添加成功结果
	 * 
	 * @return 添加成功结果
	 */
	public static Result addOk() {
		return ok(Constants.SUCCESS_ADD_MESSAGE_KEY, null);
	}
	
	/**
	 * 创建一个添加成功结果
	 * 
	 * @param data
	 *            响应数据
	 * @return 添加成功结果
	 */
	public static Result addOk(Serializable data) {
		return ok(Constants.SUCCESS_ADD_MESSAGE_KEY, data);
	}
	
	/**
	 * 创建一个更新成功结果
	 * 
	 * @return 更新成功结果
	 */
	public static Result updateOk() {
		return ok(Constants.SUCCESS_UPDATE_MESSAGE_KEY, null);
	}
	
	/**
	 * 创建一个删除成功结果
	 * 
	 * @return 删除成功结果
	 */
	public static Result delOk() {
		return ok(Constants.SUCCESS_DELETE_MESSAGE_KEY, null);
	}
	
	/**
	 * 创建一个删除成功结果
	 * 
	 * @param data
	 *            响应数据
	 * @return 删除成功结果
	 */
	public static Result delOk(Serializable data) {
		return ok(Constants.SUCCESS_DELETE_MESSAGE_KEY, data);
	}
	
	/**
	 * 创建一个上传成功结果
	 * 
	 * @param data
	 *            响应数据
	 * @return 上传成功结果
	 */
	public static Result uploadOk(Serializable data) {
		return ok(Constants.SUCCESS_UPLOAD_MESSAGE_KEY, data);
	}
	
	/**
	 * 创建一个操作成功结果
	 * 
	 * @return 操作成功结果
	 */
	public static Result opOk() {
		return ok(Constants.SUCCESS_OP_MESSAGE_KEY, null);
	}
	
	/**
	 * 创建一个操作成功结果
	 * 
	 * @param message
	 *            响应消息
	 * @return 操作成功结果
	 */
	public static Result opOk(String message) {
		return ok(Constants.SUCCESS_OP_MESSAGE_KEY, message);
	}
	
	// ================================= 错误响应部分 ====================================
	
	/**
	 * 创建失败响应结果DTO
	 * 
	 * @param code
	 *            响应吗
	 * @param message
	 *            响应消息
	 * @param data
	 *            响应数据
	 * @return 失败响应结果DTO
	 */
	public static Result error(String code, String message, Serializable data) {
		if (data == null) {
			data = (Serializable) Collections.EMPTY_MAP;
		}
		return new Result(code, SpringIoC.getMessage(message), false, data);
	}
	
	/**
	 * 根据Spring MVC提供的验证结果创建参数验证失败响应结果DTO
	 * 
	 * @param r
	 *            BindingResult
	 * @return 参数验证失败响应结果
	 */
	public static Result paramError(BindingResult r) {
		HashMap<String, String> data = new HashMap<>();
		List<ObjectError> errors = r.getAllErrors();
		errors.forEach(err -> {
			if (err instanceof FieldError) {// 字段错误
				FieldError fieldErr = (FieldError) err;
				data.put(fieldErr.getObjectName() + "->" + fieldErr.getField(), fieldErr.getObjectName() + "->" + fieldErr.getField() + "=" + fieldErr.getRejectedValue() + " message:" + fieldErr.getDefaultMessage());
			} else {// 对象错误
				data.put(err.getObjectName(), err.getObjectName() + " message:" + err.getDefaultMessage());
			}
		});
		
		return error(Constants.ERROR_PARAM_CODE, Constants.ERROR_PARAM_MESSAGE_KEY, data);
	}
	
	/**
	 * 创建失败响应结果DTO
	 * 
	 * @param code
	 *            响应吗
	 * @param message
	 *            响应消息
	 * @return 失败响应结果DTO
	 */
	public static Result error(String code, String message) {
		return error(code, message, null);
	}
	
	/**
	 * 创建失败响应结果DTO
	 * 
	 * @param code
	 *            响应吗
	 * @return 失败响应结果DTO
	 */
	public static Result error(String message) {
		return error(Constants.ERROR_CODE, message, null);
	}
	
	/**
	 * 创建失败响应结果DTO
	 * 
	 * @return 失败响应结果DTO
	 */
	public static Result error() {
		return error(Constants.ERROR_CODE, Constants.ERROR_MESSAGE_KEY);
	}
	
	/**
	 * 创建一个查询失败结果
	 * 
	 * @return 查询失败结果
	 */
	public static Result queryError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_QUERY_MESSAGE_KEY);
	}

	/**
	 * 创建一个添加失败结果
	 * 
	 * @return 添加失败结果
	 */
	public static Result addError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_ADD_MESSAGE_KEY);
	}

	/**
	 * 创建一个更新失败结果
	 * 
	 * @return 添加失败结果
	 */
	public static Result updateError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_UPDATE_MESSAGE_KEY);
	}
	
	/**
	 * 创建一个删除失败结果
	 * 
	 * @return 删除失败结果
	 */
	public static Result delError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_DELETE_MESSAGE_KEY);
	}
	
	/**
	 * 创建一个上传失败结果
	 * 
	 * @return 上传失败结果
	 */
	public static Result uploadError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_UPLOAD_MESSAGE_KEY);
	}
	
	/**
	 * 创建一个操作失败结果
	 * 
	 * @return 操作失败结果
	 */
	public static Result opError() {
		return error(Constants.ERROR_CODE, Constants.ERROR_OP_MESSAGE_KEY);
	}
	
	/**
	 * 创建一个操作失败结果
	 * 
	 * @param message
	 *            响应消息
	 * @return 操作失败结果
	 */
	public static Result opError(String message) {
		return error(Constants.ERROR_CODE, message);
	}
	
	/**
	 * 创建一个短信验证码错误结果
	 * 
	 * @return 短信验证码错误结果
	 */
	public static Result codeError() {
		return error(Constants.ERROR_CODE, "短信验证码不正确！");
	}
	
	/**
	 * 创建一个短信验证码失效结果
	 * 
	 * @return 短信验证码失效结果
	 */
	public static Result codeInvalidError() {
		return error(Constants.ERROR_CODE, "短信验证码失效！");
	}
	
	/**
	 * 创建一个手机号已注册结果
	 * 
	 * @return 手机号已注册结果
	 */
	public static Result telExistError() {
		return error(Constants.ERROR_CODE, "手机号已注册！");
	}
	
	/**
	 * 创建一个手机号未注册结果
	 * 
	 * @return 手机号未注册结果
	 */
	public static Result telNonExistError() {
		return error(Constants.ERROR_CODE, "手机号未注册！");
	}
	
	/**
	 * 创建一个推荐人不存在结果
	 * 
	 * @return 推荐人不存在结果
	 */
	public static Result refTelNonExistError() {
		return error(Constants.ERROR_CODE, "推荐人不存在！");
	}
	
	/**
	 * 创建一个无效的推荐人结果
	 * 
	 * @return 无效的推荐人结果
	 */
	public static Result refValidError() {
		return error(Constants.ERROR_CODE, "无效的推荐人！");
	}
	
	/**
	 * 创建一个用户名或密码错误结果
	 * 
	 * @return 用户名或密码错误结果
	 */
	public static Result usernameOrPasswordError() {
		return error(Constants.ERROR_CODE, "用户名或密码错误！");
	}
	
	/**
	 * 创建一个用户未登录错误结果
	 * 
	 * @return 用户未登录错误结果
	 */
	public static Result notLoginError() {
		return error(Constants.ERROR_NOT_LOGIN_CODE, "用户未登录！");
	}

}
