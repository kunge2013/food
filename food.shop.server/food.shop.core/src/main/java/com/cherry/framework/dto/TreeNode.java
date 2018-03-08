package com.cherry.framework.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点
 * 
 * @author 赵凡
 * @since 2017年11月14日
 * @version 1.0
 */
public interface TreeNode {

	/**
	 * 获取节点ID
	 * 
	 * @return 节点ID
	 */
	Serializable getId();
	
	/**
	 * 获取父节点ID
	 * 
	 * @return 父节点ID
	 */
	Serializable getParentId();
	
	/**
	 * 设置父节点ID
	 * 
	 * @param parentId
	 *            父节点ID
	 */
	void setParentId(Serializable parentId);
	
	/**
	 * 获取节点文本内容
	 * 
	 * @return 节点文本内容
	 */
	String getText();
	
	/**
	 * 设置节点文本内容
	 * 
	 * @param text
	 *            节点文本内容
	 */
	void setText(String text);
	
	/**
	 * 添加子节点
	 * 
	 * @param children
	 *            子节点
	 */
	void addChildren(TreeNode children);
	
	/**
	 * 获取子节点列表
	 * 
	 * @return 子节点列表
	 */
	List<? extends TreeNode> getChildren();
	
}
