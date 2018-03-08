package com.cherry.framework.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.cherry.framework.utils.Constants;
import com.cherry.shop.security.entity.SysUser;
import com.cherry.shop.security.service.SysUserService;

/**
 * Manager Realm
 * 
 * @author 赵凡
 * @since 2017年11月13日
 * @version 1.0
 */
public class ManagerRealm extends AuthorizingRealm {

	private SysUserService sysUserService;
	
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public String getName() {
		return "ManagerRealm";
	}

	// 认证/登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取客户端提交的登录名
		Object principal = token.getPrincipal();
		if (principal == null) {
			throw new IncorrectCredentialsException();
		}
		String username = principal.toString();
		
		// 通过用户名查询系统用户
		SysUser sysUser = sysUserService.getSysUserByUsername(username);
		
		// 账号不存在
		if (sysUser == null) {
			throw new UnknownAccountException();
		}
		
		// 检查账号可用性
		if (sysUser.getStatus() == Constants.STATUS_DISABLE) {
			throw new DisabledAccountException();
		}
		
		// 返回认证信息
		return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), this.getName());
	}
	
	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
//		List<String> funcs = sysUserService.selectFuncBySysUserId(sysUser.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//info.addStringPermissions(funcs);
		return info;
	}

}
