package com.KoreaIT.example.JAM.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dao.ArticleDao;
import com.KoreaIT.example.JAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		this.articleDao = Container.articleDao;
	}

	public int doWrite(int memberId, String title, String body) {
		return articleDao.doWrite(memberId, title, body);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public List<Article> getForPrintArticles(int page, int itemsInAPage, String searchKeyword) {
		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		Map<String, Object> args = new HashMap<>();
		args.put("searchKeyword", searchKeyword);
		args.put("limitFrom", limitFrom);
		args.put("limitTake", limitTake);

		return articleDao.getForPrintArticles(args);
	}

	public int getArticlesCount(int id) {
		return articleDao.getArticlesCount(id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public void doModify(int id, String title, String body) {
		articleDao.doModify(id, title, body);

	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

}