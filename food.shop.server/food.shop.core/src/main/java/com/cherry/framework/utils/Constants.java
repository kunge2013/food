package com.cherry.framework.utils;

/**
 * 系统常量
 * 
 * @author 赵凡
 * @since 2017年11月10日
 * @version 1.0
 */
public interface Constants {
	
	// ========================= 响应码定义部分 ==========================
	
	/**
	 * 成功响应码
	 * 
	 */
	public static final String SUCCESS_CODE = "0";
	
	/**
	 * 通用操作响应码
	 * 
	 */
	public static final String ERROR_CODE = "1";
	
	/**
	 * 参数错误响应码
	 * 
	 */
	public static final String ERROR_PARAM_CODE = "2";
	
	/**
	 * 未登录错误响应码
	 * 
	 */
	public static final String ERROR_NOT_LOGIN_CODE = "4";
	
	// ========================= 响应消息键定义部分 ==========================

	/**
	 * 成功消息键
	 * 
	 */
	public static final String SUCCESS_QUERY_MESSAGE_KEY = "result.success.query.message";
	
	/**
	 * 添加成功消息键
	 * 
	 */
	public static final String SUCCESS_ADD_MESSAGE_KEY = "result.success.add.message";
	
	/**
	 * 更新成功消息键
	 * 
	 */
	public static final String SUCCESS_UPDATE_MESSAGE_KEY = "result.success.update.message";
	
	/**
	 * 删除成功消息键
	 * 
	 */
	public static final String SUCCESS_DELETE_MESSAGE_KEY = "result.success.delete.message";
	
	/**
	 * 上传成功消息键
	 * 
	 */
	public static final String SUCCESS_UPLOAD_MESSAGE_KEY = "result.success.upload.message";
	
	/**
	 * 操作成功消息键
	 * 
	 */
	public static final String SUCCESS_OP_MESSAGE_KEY = "result.success.op.message";
	
	/**
	 * 失败消息键
	 * 
	 */
	public static final String ERROR_MESSAGE_KEY = "result.error.message";
	
	/**
	 * 查询错误消息键
	 * 
	 */
	public static final String ERROR_QUERY_MESSAGE_KEY = "result.error.query.message";
	
	/**
	 * 添加错误消息键
	 * 
	 */
	public static final String ERROR_ADD_MESSAGE_KEY = "result.error.add.message";
	
	/**
	 * 更新错误消息键
	 * 
	 */
	public static final String ERROR_UPDATE_MESSAGE_KEY = "result.error.update.message";
	
	/**
	 * 删除错误消息键
	 * 
	 */
	public static final String ERROR_DELETE_MESSAGE_KEY = "result.error.delete.message";
	
	/**
	 * 上传错误消息键
	 * 
	 */
	public static final String ERROR_UPLOAD_MESSAGE_KEY = "result.error.upload.message";
	
	/**
	 * 操作错误消息键
	 * 
	 */
	public static final String ERROR_OP_MESSAGE_KEY = "result.error.op.message";
	
	/**
	 * 参数错误消息键
	 * 
	 */
	public static final String ERROR_PARAM_MESSAGE_KEY = "result.error.param.message";
	
	// ========================= 状态常量定义部分 ==========================
	
	/**
	 * 激活状态
	 * 
	 */
	public static final String STATUS_ENABLE = "Y";
	
	/**
	 * 禁用状态
	 * 
	 */
	public static final String STATUS_DISABLE = "N";
	
	/**
	 * 性别：保密
	 * 
	 */
	public static final String SEX_UNKNOW = "U";
	
	/**
	 * 性别：男
	 * 
	 */
	public static final String SEX_MAN = "M";
	
	/**
	 * 性别：女
	 * 
	 */
	public static final String SEX_FEMALE = "F";
	
	/**
	 * 角色：管理者
	 * 
	 */
	public static final String ROLE_MANAGER = "M";
	
	/**
	 * 角色：员工
	 * 
	 */
	public static final String ROLE_EMPLOYEE = "E";
	
	// ======================= 图片模块常量定义部分 =========================
	
	/**
	 * 文件目录：系统用户文件目录
	 * 
	 */
	public static final String FILE_DIR_SYSUSER = "sysuser";
	
	/**
	 * 文件目录：用户文件目录
	 * 
	 */
	public static final String FILE_DIR_USER = "user";
	
	/**
	 * 文件目录：轮播图文件目录
	 * 
	 */
	public static final String FILE_DIR_CAROUSEL = "carousel";
	
	/**
	 * 文件目录：产品分类文件目录
	 * 
	 */
	public static final String FILE_DIR_PRODUCT_TYPE = "producttype";
	
	/**
	 * 文件目录：产品文件目录
	 * 
	 */
	public static final String FILE_DIR_PRODUCT = "product";
	
	/**
	 * 文章目录：文章标题目录
	 * 
	 */
	public static final String FILE_DIR_ARTICLE = "article";
	
	/**
	 * 文件目录：公司企业信息文件目录
	 * 
	 */
	public static final String FILE_DIR_COMPANY_INFO = "companyinfo";
	
	/**
	 * 系统用户菜单会话属性键
	 * 
	 */
	public static final String SYS_MENU_KEY = "sys_menu_key";

	/**
	 * 短信验证码缓存键前缀
	 * 
	 */
	public static final String CAPTCHA_CODE_PREFIX = "CAPTCHA_CODE:::";

}
