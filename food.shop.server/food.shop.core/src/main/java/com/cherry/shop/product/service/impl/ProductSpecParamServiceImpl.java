package com.cherry.shop.product.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.product.entity.ProductSpecParam;
import com.cherry.shop.product.mapper.ProductSpecParamMapper;
import com.cherry.shop.product.param.SaveProductSpecParamsParam;
import com.cherry.shop.product.service.ProductSpecParamService;

/**
 * 产品规格参数Service实现类
 * 
 * @author 赵凡
 * @since 2018年1月16日
 * @version 1.0
 */
@Service
public class ProductSpecParamServiceImpl extends ServiceImpl<ProductSpecParamMapper, ProductSpecParam> implements ProductSpecParamService {

	@Override
	public void saveProductSpecParams(String productId, List<SaveProductSpecParamsParam> specParams) {
		// 先删除
		this.delete(
			SQLHelper.build(ProductSpecParam.class)
					 .mustEq("productId", productId)
					 .geEntityWrapper()
		);
		
		// 后重建
		List<ProductSpecParam> productSpecs = specParams.stream().map(p -> {
			ProductSpecParam s = new ProductSpecParam();
			BeanUtils.copyProperties(p, s);
			s.setProductId(productId);
			s.setCreateTime(new Date());
			return s;
		}).collect(toList());
		this.insertBatch(productSpecs);
	}

	@Override
	public List<ProductSpecParam> listProductSpecParam(String productId) {
		return this.selectList(
			SQLHelper.build(ProductSpecParam.class)
					 .mustEq("productId", productId)
					 .orderBy("createTime", false)
					 .geEntityWrapper()
			);
	}

	@Override
	public void deleteProductSpecParams(List<String> productIds) {
		this.delete(
				SQLHelper.build(ProductSpecParam.class)
						 .in("productId", productIds)
						 .geEntityWrapper()
		);
	}

}
