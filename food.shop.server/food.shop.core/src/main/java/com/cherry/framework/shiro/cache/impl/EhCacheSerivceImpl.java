package com.cherry.framework.shiro.cache.impl;

import com.cherry.framework.shiro.cache.CacheService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache服务实现类
 * 
 * @author 赵凡
 * @since 2017年12月13日
 * @version 1.0
 */
public class EhCacheSerivceImpl implements CacheService {

	private Cache cache;
	
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
		this.cache = cacheManager.getCache("cacheService");
	}

	@Override
	public Object get(String key) {
		Element ele = cache.get(key);
		return ele == null ? null : ele.getObjectValue();
	}

	@Override
	public Object get(String key, Integer ttl) {
		Object value = get(key);
		set(key, value, ttl);
		return value;
	}

	@Override
	public void set(String key, Object value) {
		set(key, value, DEFAULT_TTL);
	}

	@Override
	public void set(String key, Object value, int ttl) {
		Element ele = new Element(key, value);
		ele.setTimeToLive(ttl);
		cache.put(ele);
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}

}
