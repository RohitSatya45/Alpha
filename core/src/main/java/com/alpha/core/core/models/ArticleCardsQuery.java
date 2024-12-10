package com.alpha.core.core.models;

import com.alpha.core.core.beans.ArticleDetailsHelper;

import java.util.List;

public interface ArticleCardsQuery {
String getArticleRootPath();
List<ArticleDetailsHelper>getArticleDetails();
}
