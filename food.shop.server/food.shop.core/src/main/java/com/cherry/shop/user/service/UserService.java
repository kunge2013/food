package com.cherry.shop.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.cherry.framework.dto.Result;
import com.cherry.shop.user.dto.api.GetInfoDto;
import com.cherry.shop.user.entity.User;

/**
 * 用户Service接口
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
public interface UserService extends IService<User> {
	
	/**
	 * 获取当前用户-登录用户在数据库中的最新映射
	 * 
	 * @return 当前用户
	 */
	User getCurUser();
	
	/**
	 * 获取登录用户
	 * 
	 * @return 登录用户
	 */
	User getLoginUser();

	/**
	 * 通过用户名查询用户信息
	 * 
	 * @param username
	 *            用户名
	 * @return 用户信息
	 */
	User getUserByUsername(String username);
	
	/**
	 * 通过手机号查询用户信息
	 * 
	 * @param telephone
	 *            手机号
	 * @return 用户信息
	 */
	User getUserByTelephone(String telephone);

	/**
	 * 注册用户
	 * 
	 * @param user
	 *            用户信息
	 * @return 注册结果
	 */
	Result insertUser(User user);

	/**
	 * 通过手机号更新密码
	 * 
	 * @param telephone
	 *            手机号
	 * @param password
	 *            新密码
	 * @return 更新结果
	 */
	Result updatePasswordByTelephone(String telephone, String password);

	/**
	 * 获取个人信息
	 * 
	 * @return 个人信息
	 */
	GetInfoDto getInfo();

}
