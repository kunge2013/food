package com.cherry.framework.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.cherry.shop.user.entity.User;
import com.cherry.shop.user.service.UserService;

/**
 * API Realm
 * 
 * @author 赵凡
 * @since 2017年11月24日
 * @version 1.0
 */
public class ApiRealm extends AuthorizingRealm {

	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public String getName() {
		return "ApiRealm";
	}
	
	// 认证/登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取客户端提交的用户凭证
		Object principal = token.getPrincipal();
		if (principal == null) {
			throw new IncorrectCredentialsException();
		}
		String username = principal.toString();

		// 通过用户名查询用户
		User user = userService.getUserByUsername(username);
		
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException();
		}
		
		// 返回认证信息
		return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}
	
}
