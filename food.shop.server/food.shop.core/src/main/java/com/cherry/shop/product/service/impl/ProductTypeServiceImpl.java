package com.cherry.shop.product.service.impl;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.param.ToggleSwitchParam;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.product.dto.ProductTypeDto;
import com.cherry.shop.product.entity.ProductType;
import com.cherry.shop.product.mapper.ProductTypeMapper;
import com.cherry.shop.product.param.AddProductTypeParam;
import com.cherry.shop.product.param.ListProductTypeParam;
import com.cherry.shop.product.param.UpdateProductTypeParam;
import com.cherry.shop.product.service.ProductService;
import com.cherry.shop.product.service.ProductTypeService;

/**
 * 产品分类Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月10日
 * @version 1.0
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

	@Autowired
	private ProductService productService;
	
	@Override
	public List<ProductTypeDto> listProductType(ListProductTypeParam params) {
		// 查询产品分类列表
		List<ProductType> productTypes = this.selectList(
			SQLHelper.build(ProductType.class)
					 .eqOrBlank("parentId", params.getParentId())
					 .eq("show", params.getShow())
					 .eq("promotion", params.getPromotion())
					 .orderBy(params.getOrderByField(), params.isAsc())
					 .geEntityWrapper()
		);
		
		// 查询父类名称
		final String parentName = StringUtils.isBlank(params.getParentId()) ? null :
				Optional.of(this.selectById(params.getParentId()))// 查询父类信息
						.orElseGet(ProductType::new)// 如果没有父类，返回一个空的父类
						.getName();// 获取父类名称
		
		// 转换为Dto
		return productTypes.stream().map(t -> {
			ProductTypeDto dto = new ProductTypeDto();
			BeanUtils.copyProperties(t, dto);
			dto.setParentName(parentName);
			return dto;
		}).collect(toList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> listTypeIdByParentAndSelf(String typeId) {
		if (StringUtils.isBlank(typeId)) {
			return Collections.EMPTY_LIST;
		}
		
		// 查找潜在的子孙类型列表
		List<ProductType> productTypes = this.selectList(
			SQLHelper.build(ProductType.class)
					 .notEqStr("id", typeId)// 排除自己
					 .isNotNull("parentId")// 排除顶级元素
					 .geEntityWrapper()
		);
		
		// 真实存在的子孙类型和自己所在的结果容器
		List<String> typeIds = new ArrayList<>();
		// 添加自己
		typeIds.add(typeId);
		// 父节点ID
		List<String> parentIds = new ArrayList<>();
		parentIds.add(typeId);
		
		// 查找实际存在的子孙类型
		findChild(typeIds, productTypes, parentIds);
		
		return typeIds;
	}
	
	private void findChild(List<String> typeIds, List<ProductType> nodes, final List<String> parentIds) {
		if (CollectionUtils.isNotEmpty(parentIds)) {
			// 查找子节点
			List<ProductType> childs = nodes.stream().filter(t -> {
				return !parentIds.contains(t.getParentId());
			}).collect(toList());
			
			if (childs.size() > 0) {
				// 从查找列表中剔除已经找到的子节点
				nodes.removeAll(childs);
				// 提取找到的分类ID
				List<String> childIds = childs.stream().map(ProductType::getId).collect(toList());
				// 添加到结果列表中
				typeIds.addAll(childIds);
				// 当前找到的节点的Id列表
				findChild(typeIds, nodes, childIds);
			}
		}
	}

	@Override
	public List<ProductType> listLeafType() {
		// 查询所有的父类ID
		List<String> parentIds = this.selectList(
			SQLHelper.build(ProductType.class)
					 .isNotNull("parentId")// 父ID不为空
					 .geEntityWrapper()
		).stream()
		 .map(ProductType::getParentId)
		 .distinct()
		 .collect(toList());
		
		// 查询叶子节点类型列表
		return this.selectList(
			SQLHelper.build(ProductType.class)
					 .notIn("id", parentIds)
					 .geEntityWrapper()
		).stream().filter(t -> {
			return t.isShow() && !t.isPromotion();
		}).collect(toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> listProductTypeByIds(List<String> typeIds) {
		if (typeIds == null || typeIds.size() == 0) {
			return Collections.EMPTY_LIST;
		}
		return this.selectBatchIds(typeIds);
	}

	@Override
	public List<ProductType> listProductTypeByParentId(String parentId) {
		return this.selectList(
			SQLHelper.build(ProductType.class)
					 .eqOrBlank("parentId", parentId)
					 .eq("show", true)
					 .orderBy("order", true)
					 .geEntityWrapper()
		);
	}

	@Override
	public Map<String, ProductType> getProductTypeMapper(List<String> typeIds) {
		return listProductTypeByIds(typeIds).stream().collect(toMap(ProductType::getId, Function.identity()));
	}

	@Override
	public ProductType getProductType(String productTypeId) {
		return this.selectById(productTypeId);
	}

	@Override
	public void addProductType(AddProductTypeParam params) {
		ProductType productType = new ProductType();
		BeanUtils.copyProperties(params, productType);
		productType.setCreateTime(new Date());
		this.insert(productType);
	}

	@Override
	public void updateProductType(UpdateProductTypeParam params) {
		ProductType productType = new ProductType();
		BeanUtils.copyProperties(params, productType);
		productType.setCreateTime(new Date());
		this.updateById(productType);
	}

	@Override
	public void deleteProductTypeByIds(List<String> productTypeIds) {
		// 查找分类及其子孙分类
		List<String> deleteTypeIds = new ArrayList<>();
		productTypeIds.forEach(typeId -> {
			deleteTypeIds.addAll(listTypeIdByParentAndSelf(typeId));
		});
		
		// 删除分类及其子孙分类
		this.deleteBatchIds(deleteTypeIds);
		
		// 删除分类下的产品
		productService.deleteProductsByType(deleteTypeIds);
	}

	@Override
	public void toggleShow(ToggleSwitchParam params) {
		ProductType productType = new ProductType();
		productType.setId(params.getId());
		productType.setShow(params.getOpen());
		this.updateById(productType);
	}

	@Override
	public void togglePromotion(ToggleSwitchParam params) {
		ProductType productType = new ProductType();
		productType.setId(params.getId());
		productType.setPromotion(params.getOpen());
		this.updateById(productType);
	}

}
