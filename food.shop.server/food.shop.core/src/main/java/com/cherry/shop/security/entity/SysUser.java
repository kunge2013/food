package com.cherry.shop.security.entity;

import java.util.Date;

import com.cherry.framework.entity.BaseEntity;
import com.cherry.framework.shiro.utils.Encryptable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户实体类
 * 
 * @author 赵凡
 * @since 2017年12月28日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity implements Encryptable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7237879277463898774L;

	private String username;// 用户名
	
	private String image;// 头像
	
	private String password;// 密码
	
	private String salt;// 盐
	
	private String realName;// 真实姓名
	
	private Date birthday;// 出生年月
	
	private String sex;// 性别：M、男；F、女；U、未知
	
	private String telephone;// 手机号
	
	private String email;// 邮箱地址
	
	private String status;// 用户状态 Y：激活、N：禁用
	
	private Date createTime;// 创建时间
	
	private String creatorId;// 创建者

	@Override
	public String getPlainText() {
		return this.password;
	}

	@Override
	public void setCipherText(String cipherText) {
		this.password = cipherText;
	}
	
}
