package com.alpha.core.core.models.impl;

import com.alpha.core.core.beans.ArticleDetailsHelper;
import com.alpha.core.core.models.ArticleCardsQuery;
import com.alpha.core.core.utils.ResolverUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables= Resource.class,adapters = ArticleCardsQuery.class)
public class ArticleCardsQueryImpl implements ArticleCardsQuery {
    private static final Logger Log= LoggerFactory.getLogger(ArticleCardsQueryImpl.class);
    @Inject
    ResourceResolverFactory resourceResolverFactory;
    @Inject
    String articlepath;
    List<ArticleDetailsHelper> details=new ArrayList<>();

    @PostConstruct
    protected void init() {
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            Log.info("ResourceResolver"+resourceResolver);
            Session session=resourceResolver.adaptTo(Session.class);
            QueryBuilder queryBuilder=resourceResolver.adaptTo(QueryBuilder.class);
            Map<String,String> predicate=new HashMap<>();
            predicate.put("path",articlepath);
            predicate.put("type","cq:Page");//i gave small p that wasted some time
            Query query=null;
            try{
                query = queryBuilder.createQuery(PredicateGroup.create(predicate),session);
                Log.info("\n Query : {}",query);
            } catch (Exception e) {
                Log.info("\n QuryBuilder Error : {}",e.getMessage());
            }
            SearchResult searchResult=query.getResult();//search results represent searcresults of a jcr quey.
            for(Hit hit:searchResult.getHits()){ //hit reprsents singlr search result(node and resource).
                ArticleDetailsHelper articleDetailsHelper =new ArticleDetailsHelper();
                String path=null;
                try{
                    path=hit.getPath();//it will give the path.
                    Resource articleResource=resourceResolver.getResource(path);
                    Page articlePage = articleResource.adaptTo(Page.class);
                    String title= articlePage.getTitle();
                    String description= articlePage.getDescription();
                    articleDetailsHelper.setPath(path);
                    articleDetailsHelper.setTitle(title);
                    articleDetailsHelper.setDescription(description);
                    Log.info("Title : {},Description : {}",title,description);
                    details.add(articleDetailsHelper);
                }catch(Exception e){
                    Log.info("\n no hits found {}",e.getMessage());
                }
            }
        } catch (LoginException e) {
            Log.info("\n ResoureResolverError : {}",e.getMessage());
        }
    }

    @Override
    public String getArticleRootPath() {
        return articlepath;//https://github.com/RohitSatya45/Alpha.git
    }

    @Override
    public List<ArticleDetailsHelper> getArticleDetails() {
        return details;
    }
}

