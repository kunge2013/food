package com.cherry.shop.system.dto;

import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.cherry.shop.system.entity.Config;
import com.cherry.shop.system.param.ConfigName;

import lombok.Data;

/**
 * 配置块DTO
 * 
 * @author 赵凡
 * @since 2018年1月9日
 * @version 1.0
 */
@Data
public class ConfigBlockDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961306956810782497L;

	private String block;// 配置块
	
	private Map<String, Config> configMapper;// 配置映射器
	
	/**
	 * 创建一个空的配置块
	 * 
	 * @param block
	 *            配置块
	 */
	@SuppressWarnings("unchecked")
	public ConfigBlockDto(String block) {
		this(block, Collections.EMPTY_LIST);
	}
	
	/**
	 * 创建一个配置块
	 * 
	 * @param block
	 *            配置块
	 * @param configMapper
	 * 			  配置块映射表
	 */
	public ConfigBlockDto(String block, List<Config> configs) {
		super();
		this.block = block;
		// 将配置列表转换为属性名->Config的映射
		this.configMapper = configs.parallelStream().collect(toMap(Config::getName, Function.identity()));
	}
	
	/**
	 * 通过属性名获取配置
	 * 
	 * @param name
	 *            属性名
	 * @return 配置对象
	 */
	protected Optional<Config> getConfig(ConfigName name) {
		return Optional.ofNullable(configMapper.get(name.getKey()));
	}
	
	/**
	 * 通过属性名获取属性值
	 * 
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	public String getValue(ConfigName name) {
		return getConfig(name).orElseGet(Config::new).getValue();
	}
	
	/**
	 * 通过属性名获取属性值
	 * 
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	public BigDecimal getBigDecimal(ConfigName name) {
		return new BigDecimal(getValue(name));
	}
	
	/**
	 * 向当前配置块中添加配置属性
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 当前配置块
	 */
	public ConfigBlockDto addConfig(ConfigName name, String value) {
		configMapper.put(name.getKey(), new Config(block, name.getKey(), value));
		return this;
	}

}
