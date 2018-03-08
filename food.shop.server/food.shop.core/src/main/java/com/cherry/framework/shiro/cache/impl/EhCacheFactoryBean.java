package com.cherry.framework.shiro.cache.impl;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import com.cherry.framework.shiro.cache.CacheService;

import net.sf.ehcache.CacheManager;

/**
 * EhCacheService工厂Bean，用于初始化EhCache服务
 * 
 * @author 赵凡
 * @since 2017年11月23日
 * @version 1.0
 */
public class EhCacheFactoryBean implements FactoryBean<CacheService>, DisposableBean {

	// CacheManager
	private CacheManager cacheManager;
	
	/**
	 * 获取CacheManager
	 * 
	 * @return CacheManager
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 设置CacheManager
	 * 
	 * @param cacheManager
	 *            CacheManager
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public CacheService getObject() throws Exception {
		EhCacheSerivceImpl service = new EhCacheSerivceImpl();
		cacheManager = CacheManager.getInstance();
		service.setCacheManager(cacheManager);
		return service;
	}

	@Override
	public Class<?> getObjectType() {
		return CacheService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
	}

}
