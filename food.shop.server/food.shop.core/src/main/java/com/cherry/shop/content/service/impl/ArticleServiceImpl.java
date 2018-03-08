package com.cherry.shop.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cherry.framework.utils.SQLHelper;
import com.cherry.shop.content.entity.Article;
import com.cherry.shop.content.mapper.ArticleMapper;
import com.cherry.shop.content.param.AddArticleParam;
import com.cherry.shop.content.param.ListArticleParam;
import com.cherry.shop.content.param.UpdateArticleParam;
import com.cherry.shop.content.service.ArticleService;

/**
 * 文章service实现类
 * @author cl
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

	@Override
	public Page<Article> listArticle(Page<Article> page,ListArticleParam params) {
		
		return this.selectPage(page,SQLHelper.build(Article.class)
				.like("title", params.getTitle())
				.orderBy("createTime", params.isAsc())
				.geEntityWrapper());
	}

	@Override
	public void addArticle(AddArticleParam params) {
		Article article = new Article();
		BeanUtils.copyProperties(params, article);
		article.setCreateTime(new Date());
		this.insert(article);
	}

	@Override
	public void updateArticle(UpdateArticleParam params) {
		Article article = new Article();
		BeanUtils.copyProperties(params, article);
		this.updateById(article);
	}

	@Override
	public void deleteArticleByIds(List<String> articleIds) {
		this.deleteBatchIds(articleIds);
		
	}

	@Override
	public Article getArticle(String articleId) {
		
		return this.selectById(articleId);
	}

}
