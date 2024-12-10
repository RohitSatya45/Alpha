package com.alpha.core.core.services.impl;

import com.alpha.core.core.services.ResourceTypeUpdate;
import com.alpha.core.core.utils.ResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import java.util.Iterator;

@Component(service= ResourceTypeUpdate.class,immediate = true)
public class ResourceTypeUpdateImpl implements ResourceTypeUpdate {
    private static final Logger log = LoggerFactory.getLogger(ResourceTypeUpdateImpl.class);
    @Reference
    ResourceResolverFactory resourceResolverFactory;
    @Activate
    public void slingResourceUpdate() {
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            Page page = resourceResolver.adaptTo(PageManager.class).getPage("/content/alpha/us/en");
            Iterator<Page> pages = page.listChildren();
            while (pages.hasNext()) {
                Page childPage = pages.next();
                log.info("\n page:{}",childPage);
                nodeConvertor(resourceResolver,childPage);
            }
        } catch (Exception e) {
            log.info("\n Error Message :{}", e.getMessage());
        }

    }
    public void nodeConvertor(ResourceResolver resourceResolver, Page childPage) throws RepositoryException, PersistenceException {
        Node pageNode= childPage.adaptTo(Node.class);
        if(pageNode!=null){
            log.info("\n pageNodeName:{}",pageNode.getName());
            nodeIterator(resourceResolver,pageNode);
        }
    }
    public void nodeIterator(ResourceResolver resourceResolver, Node node) throws RepositoryException, PersistenceException {
        if(node.hasProperty("node")){
            node.setProperty("node","yes");
            resourceResolver.commit();
        }
        NodeIterator nodes=node.getNodes();
        while(nodes.hasNext()){
            Node childNode=nodes.nextNode();
            nodeIterator(resourceResolver,childNode);
        }
    }
}

