package com.cherry.shop.product.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.product.entity.ProductSpec;
import com.cherry.shop.product.mapper.ProductSpecMapper;
import com.cherry.shop.product.param.SaveProductSpecParam;
import com.cherry.shop.product.param.api.ListProductPriceParam;
import com.cherry.shop.product.service.ProductSpecService;

/**
 * 产品规格Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月15日
 * @version 1.0
 */
@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {

	@Override
	public Page<ProductSpec> listProductSpec(ListProductPriceParam params) {
		return this.selectPage(
			params.toPage(ProductSpec.class),
			SQLHelper.build(ProductSpec.class)
					 .mustEq("type", params.getTypeId())
					 .eq("is_sale", true)
					 .orderBy("createTime", false)
					 .geEntityWrapper()
		);
	}

	@Override
	public List<ProductSpec> listProductSpec(String... productIds) {
		return this.selectList(
			SQLHelper.build(ProductSpec.class)
				 	 .in("productId", productIds)
				 	 .orderBy("createTime", false)
				 	 .geEntityWrapper()
		);
	}

	@Override
	public void saveProductSpecs(String productId, String type, List<SaveProductSpecParam> specs) {
		// 先删除
		this.delete(
			SQLHelper.build(ProductSpec.class)
					 .mustEq("productId", productId)
					 .geEntityWrapper()
		);
		
		// 后重建
		List<ProductSpec> productSpecs = specs.stream().map(p -> {
			ProductSpec s = new ProductSpec();
			BeanUtils.copyProperties(p, s);
			s.setProductId(productId);
			s.setCreateTime(new Date());
			s.setType(type);
			return s;
		}).collect(toList());
		this.insertBatch(productSpecs);
	}

	@Override
	public void deleteProductSpecs(List<String> productIds) {
		this.delete(
			SQLHelper.build(ProductSpec.class)
					 .in("productId", productIds)
					 .geEntityWrapper()
		);
	}

}
