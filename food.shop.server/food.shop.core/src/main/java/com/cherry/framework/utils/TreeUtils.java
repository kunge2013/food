package com.cherry.framework.utils;

import java.util.ArrayList;
import java.util.List;

import com.cherry.framework.dto.TreeNode;

/**
 * 树节点工具类
 * 
 * @author 赵凡
 * @since 2017年11月14日
 * @version 1.0
 */
public class TreeUtils {

	/**
	 * 将平面节点列表转换为树节点列表
	 * 
	 * @param nodes
	 *            平面节点列表
	 * @return 树节点列表
	 */
	public static List<?> toTree(List<? extends TreeNode> nodes) {
		return toTree(nodes, NONE_NODE_PROCESSOR);
	}
	
	/**
	 * 将平面节点列表转换为树节点列表
	 * 
	 * @param nodes
	 *            平面节点列表
	 * @param p
	 *            节点处理器
	 * @return 树节点列表
	 */
	public static List<?> toTree(List<? extends TreeNode> nodes, NodeProcessor p) {
		// 从原始节点列表中剔除根节点
		List<? extends TreeNode> roots = extractRoot(nodes);
		
		// 填充子节点
		if (nodes.size() > 0) {
			int level = 0;
			fillChildrenNode(roots, nodes, ++level, p == null ? NONE_NODE_PROCESSOR : p);
		}
		
		return roots;
	}
	
	/**
	 * 从原始节点列表中剔除根节点
	 * 
	 * @param source
	 *            原始节点列表
	 * @return 根节点列表
	 */
	private static List<? extends TreeNode> extractRoot(List<? extends TreeNode> source) {
		// 存储根节点
		List<TreeNode> roots = new ArrayList<>();
		
		if (source != null) {
			// 从原始节点列表中提取根节点
			source.forEach((TreeNode n) -> {
				if (n.getParentId() == null) {
					roots.add(n);
				}
			});
			// 从原始节点列表中剔除根节点
			source.removeAll(roots);
		}
		
		return roots;
	}
	
	/**
	 * 填充子节点
	 * 
	 * @param parents
	 *            需要填充子节点的父节点列表
	 * @param childrens
	 *            剩下可添加的子节点列表
	 * @param level
	 *            层级
	 * @param p
	 *            节点处理器
	 */
	private static void fillChildrenNode(List<? extends TreeNode> parents, List<? extends TreeNode> childrens, int level, NodeProcessor p) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (TreeNode parent : parents) {
			for (TreeNode children : childrens) {
				if (children.getParentId().equals(parent.getId())) {
					p.process(children, level);
					parent.addChildren(children);
					nodes.add(children);
				}
			}
		}
		
		childrens.removeAll(nodes);
		
		if (nodes.size() > 0) {
			fillChildrenNode(nodes, childrens, ++level, p);
		}
	}
	
	/**
	 * 节点处理器
	 * 
	 * @author 赵凡
	 * @since 2017年11月28日
	 * @version 1.0
	 */
	static interface NodeProcessor {
		
		/**
		 * 处理节点
		 * 
		 * @param node
		 *            树节点
		 * @param level
		 *            层级
		 */
		void process(TreeNode node, int level);
		
	}
	
	/**
	 * 简单的节点处理器
	 * 
	 * @author 赵凡
	 * @since 2017年11月28日
	 * @version 1.0
	 */
	static class SimpleNodeProcessor implements NodeProcessor {

		@Override
		public void process(TreeNode node, int level) {
			String text = node.getText();
			for (int i = 0; i < level; i++) {
				text += " ";
			}
			node.setText(text);
		}
		
	}
	
	/**
	 * 不做任何处理
	 * 
	 * @author 赵凡
	 * @since 2017年11月28日
	 * @version 1.0
	 */
	static class NoneNodeProcessor implements NodeProcessor {

		@Override
		public void process(TreeNode node, int level) {
			
		}
		
	}
	
	/**
	 * 简单的节点处理器
	 * 
	 */
	public static final SimpleNodeProcessor SIMPLE_NODE_PROCESSOR = new SimpleNodeProcessor();

	/**
	 * 不做任何处理
	 * 
	 */
	public static final NoneNodeProcessor NONE_NODE_PROCESSOR = new NoneNodeProcessor();
	
}
