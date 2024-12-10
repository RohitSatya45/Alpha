package com.alpha.core.core.services.impl;


import com.alpha.core.core.services.ServiceOne;
import com.alpha.core.core.utils.ResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

@Component(service= ServiceOne.class,immediate = true)
public class ServiceOneImpl  implements ServiceOne{
    private static final Logger LOG= LoggerFactory.getLogger(ServiceOneImpl.class);

    @Reference//wasted half a day
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE ACTIVATE================");
        LOG.info("\n {} = {} ",componentContext.getBundleContext().getBundle().getBundleId(),componentContext.getBundleContext().getBundle().getSymbolicName());
    }
    @Deactivate
    public void deactivate(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE DEACTIVATE================");
    }
    @Modified
    public void modified(ComponentContext componentContext){
        LOG.info("\n ==============INSIDE MODIFIED================");
    }

    @Override
    public Iterator<Page> getPagesOne() {
        try{
            ResourceResolver resourceResolver= ResolverUtil.newResolver(resourceResolverFactory);
            LOG.info("\n Meesage:{} ", resourceResolver);
            PageManager pageManager=resourceResolver.adaptTo(PageManager.class);
            Page page=pageManager.getPage("/content/alpha/us/en/cloud/testname");
            LOG.info("\n pagepath: {} ", page);
            Iterator<Page> pages=page.listChildren();
            return pages;
        } catch (Exception e) {
            LOG.info("\n Error Message: {} ",e.getMessage());
        }
        return null;
    }
}
