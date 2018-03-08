package com.cherry.shop.user.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.cherry.framework.entity.BaseEntity;
import com.cherry.framework.shiro.utils.Encryptable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 * 
 * @author 赵凡
 * @since 2018年1月8日
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Encryptable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1032096717642266954L;
	
	private String username;// 用户名
	
	private String telephone;// 手机号
	
	private String password;// 密码
	
	private String salt;// 盐
	
	private String nickName;// 昵称
	
	private String image;// 头像
	
	private String sex;// 性别：M、男；F、女；U、保密
	
	private Date birthday;// 生日
	
	private String referrer;// 推荐人
	
	private String role;// 角色：M、企业管理者；E、企业员工
	
	private String companyId;// 所属企业ID

	private BigDecimal returnMoney;// 返现
	
	private BigDecimal walletMoney;// 钱包
	
	private Date registTime;// 注册时间

	@Override
	public String getPlainText() {
		return this.password;
	}

	@Override
	public void setCipherText(String cipherText) {
		this.password = cipherText;
	}

}
