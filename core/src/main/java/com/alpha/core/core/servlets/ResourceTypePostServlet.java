package com.alpha.core.core.servlets;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service= Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "alpha/components/page",
        methods={HttpConstants.METHOD_POST,HttpConstants.METHOD_GET}
)
public class ResourceTypePostServlet extends SlingSafeMethodsServlet {
    private static final Logger log= LoggerFactory.getLogger("ResourceTypePostServlet");
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        String resourcePath=request.getParameter("resourePath");
        String propertyName=request.getParameter("propertyName");
        String propertyValue=request.getParameter("propertyValue");
        try{
            Resource resource=resourceResolver.getResource(resourcePath);
            if(resource!=null){
                ModifiableValueMap modifiableValueMap=resource.adaptTo(ModifiableValueMap.class);
                if(modifiableValueMap!=null){
                    modifiableValueMap.put(propertyName,propertyValue);
                    resourceResolver.commit();

                }
            }
        } catch (Exception e) {
            log.info("\n Error Message {}:",e.getMessage());
        }
    }
}
