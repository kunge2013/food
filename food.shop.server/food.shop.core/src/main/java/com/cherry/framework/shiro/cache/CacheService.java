package com.cherry.framework.shiro.cache;

/**
 * 缓存服务接口
 * 
 * @author 赵凡
 * @since 2017年11月23日
 * @version 1.0
 */
public interface CacheService {

	public static final int DEFAULT_TTL = 1800;
	
	/**
	 * 获取指定键对应的缓存值
	 * 
	 * @param key
	 *            键
	 * @return 缓存值
	 */
	public Object get(String key);
	
	/**
	 * 获取指定键对应的缓存值，并续期
	 * 
	 * @param key
	 *            键
	 * @param ttl
	 *            续期周期，单位为秒
	 * @return 缓存值
	 */
	public Object get(String key, Integer ttl);
	
	/**
	 * 设置指定键对应的缓存值，默认存活时间为30分钟
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            缓存值
	 */
	public void set(String key, Object value);
	
	/**
	 * 设置指定键对应的缓存值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            缓存值
	 * @param ttl
	 *            存活时间，单位为秒
	 */
	public void set(String key, Object value, int ttl);
	
	/**
	 * 删除指定键对应的缓存值
	 * 
	 * @param key
	 *            键
	 */
	public void remove(String key);
	
}
