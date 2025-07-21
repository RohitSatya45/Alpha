package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.HelloAem;
import com.day.cq.wcm.api.Page;
import org.apache.lucene.util.QueryBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables= SlingHttpServletRequest.class,
        adapters= HelloAem.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "alpha/components/helloaem"
)
@Exporter(
        name = "jackson",
        extensions = "json"
)

public class HelloAemImpl implements HelloAem {
    private static final Logger log= LoggerFactory.getLogger(HelloAemImpl.class);
    @ValueMapValue
    @Default(values="Kandregula")
    String firstname;

    @ValueMapValue
    Boolean isprofessor;

    @ScriptVariable
    Page currentPage;

    @RequestAttribute(name="attribute")
    String attribute;

    @Inject
    @Named("jcr:lastModified")
    String lastModified;

    /*we can also use this for last modified
    @ValueMapValue(name = "jcr:lastModified")
    private String lastModified;
    */

    @Self
    SlingHttpServletRequest slingHttpServletRequest;

    @SlingObject
    ResourceResolver resourceResolver;

    @OSGiService
    QueryBuilder queryBuilder;

    @ResourcePath(path="/content/alpha/us/en/cloud/testname")
    Resource resource;

    @Override
    public String getFirstName() {
        return firstname;
    }

    @Override
    public Boolean getIsProfessor() {
        return isprofessor;
    }

    @Override
    public String getPageTitle() {
        return currentPage.getTitle();
    }

    @Override
    public String getRequestAttribute() {
        return attribute;
    }

    @Override
    public String getLastModified() {
        return lastModified;
    }

    @Override
    public String getPageName() {
        return resource.getName();
    }

    @PostConstruct
    protected void init(){
        log.info("\n Inside Init {}: {}",currentPage.getTitle(),resource.getPath());

    }

}
