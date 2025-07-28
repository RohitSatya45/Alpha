package com.alpha.core.core.models.impl;

import com.alpha.core.core.beans.ArticleDetailsHelper;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleCardsQueryImplTest {

    @Mock
    ResourceResolverFactory resourceResolverFactory;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    QueryBuilder queryBuilder;
    @Mock
    Session session;
    @Mock
    Query query;
    @Mock
    SearchResult searchResult;
    @Mock
    Hit hit;
    @Mock
    Page page;
    @Mock
    Resource hitResource;

    private ArticleCardsQueryImpl model;
    private MockedStatic<ResolverUtil> mockedStatic;

    @BeforeEach
    void setUp() throws RepositoryException, LoginException {
        // Mock static method
        mockedStatic = Mockito.mockStatic(ResolverUtil.class);
        mockedStatic.when(() -> ResolverUtil.newResolver(resourceResolverFactory)).thenReturn(resourceResolver);

        // Mock AEM API behaviors
        when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        // Mock query predicate and result
        Map<String, String> predicates = new HashMap<>();
        predicates.put("path", "/content/articles");
        predicates.put("type", "cq:Page");

        PredicateGroup pg = PredicateGroup.create(predicates);
        when(queryBuilder.createQuery(eq(pg), eq(session))).thenReturn(query);
        when(query.getResult()).thenReturn(searchResult);
        when(searchResult.getHits()).thenReturn(Collections.singletonList(hit));

        // Mock hit to page
        when(hit.getPath()).thenReturn("/content/articles/sample");
        when(resourceResolver.getResource("/content/articles/sample")).thenReturn(hitResource);
        when(hitResource.adaptTo(Page.class)).thenReturn(page);
        when(page.getTitle()).thenReturn("sample title");
        when(page.getDescription()).thenReturn("sample Description");

        // Instantiate and initialize model
        model = new ArticleCardsQueryImpl();
        model.resourceResolverFactory = resourceResolverFactory;
        model.articlepath = "/content/articles";
        model.init();
    }

    @AfterEach
    void tearDown() {
        if (mockedStatic != null) {
            mockedStatic.close();
        }
    }

    @Test
    void getArticleDetails() {
        List<ArticleDetailsHelper> details = model.getArticleDetails();
        assertNotNull(details);
        assertEquals(1, details.size());
        assertEquals("sample title", details.get(0).getTitle());
        assertEquals("sample Description", details.get(0).getDescription());
        assertEquals("/content/articles/sample", details.get(0).getPath());
    }

    @Test
    void getArticleRootPath() {
        assertEquals("/content/articles", model.getArticleRootPath());
    }
}
