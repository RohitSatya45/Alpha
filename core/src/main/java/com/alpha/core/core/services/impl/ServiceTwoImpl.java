package com.alpha.core.core.services.impl;


import com.alpha.core.core.services.MultiService;
import com.alpha.core.core.services.ServiceOne;
import com.alpha.core.core.services.ServiceTwo;
import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component (service= ServiceTwo.class, immediate = true)
public class ServiceTwoImpl implements ServiceTwo{
    private static final Logger LOG= LoggerFactory.getLogger(ServiceTwoImpl.class);
    @Reference
    ServiceOne serviceOne;

    @Reference(target="(component.name=com.alpha.core.core.services.impl.MultiServiceBImpl)")
    MultiService multiServiceb;

    @Override
    public List<String> getPageTwo() {
        List<String>listPages=new ArrayList<>();
        try{
            Iterator<Page>pages= serviceOne.getPagesOne();
            while(pages.hasNext()){
                listPages.add(pages.next().getTitle());
            }
        } catch (Exception e) {
            LOG.info("\n message :{} ",e.getMessage());

        }
        return listPages;
    }

    @Override
    public String getNames() {
        return multiServiceb.getName();
    }
}
