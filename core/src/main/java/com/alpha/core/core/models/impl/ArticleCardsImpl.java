/*package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.ArticleCards;
import com.alpha.core.core.utils.ResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = Resource.class,adapters = ArticleCards.class)
public class ArticleCardsImpl implements ArticleCards{
    private static final Logger log= LoggerFactory.getLogger(ArticleCardsImpl.class);
    @Inject
    String articlepath;
    @Inject//wasted one day
    ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<String> getTitle() {
        List<String>info=new ArrayList<>();
        try{
            ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);
            Page page= resourceResolver.adaptTo(PageManager.class).getPage(articlepath);
            log.info("\n page Name: {}",page);
            Iterator<Page>pages= page.listChildren();
            while(pages.hasNext()){
                String title=pages.next().getTitle();
                log.info("\n page Title: {}",title);
                info.add(title);
            }

        } catch (Exception e) {
            log.info("\n error message{}:",e.getMessage());
        }
        return info;
    }

}
 */
