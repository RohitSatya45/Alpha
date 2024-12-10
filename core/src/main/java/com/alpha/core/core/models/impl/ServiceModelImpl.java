package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.ServiceModel;
import com.alpha.core.core.services.MultiService;
import com.alpha.core.core.services.ServiceOne;
import com.alpha.core.core.services.ServiceTwo;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Iterator;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = ServiceModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ServiceModelImpl  implements ServiceModel {
    private static final Logger LOG= LoggerFactory.getLogger(ServiceModelImpl.class);

    /*--------Start Tutorial #29--------*/
    @OSGiService
    ServiceOne demoService;

    @OSGiService
    ServiceTwo serviceTwo;

    @OSGiService(filter="(component.name=rohit)")
    MultiService multiServicea;

    @OSGiService(filter="(component.name=com.alpha.core.core.services.impl.MultiServiceBImpl)")
    MultiService multiServiceb;



    @Override
    public Iterator<Page> getPagesList(){
        return demoService.getPagesOne();
    }

    @Override
    public List<String> getPagesTitle() {
        return serviceTwo.getPageTwo();
    }

    @Override
    public String getAuthorNamea() {
        return multiServicea.getName();
    }

    @Override
    public String getAuthorNameb() {
        return multiServiceb.getName();
    }

    @Override
    public String getName() {
        return serviceTwo.getNames();
    }

}