package com.alpha.core.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "alpha/components/page",
        methods = {HttpConstants.METHOD_POST}
)
public class ResourceTypePostServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(ResourceTypePostServlet.class);

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        String resourcePath = request.getParameter("resourcePath");
        String propertyName = request.getParameter("propertyName");
        String propertyValue = request.getParameter("propertyValue");

        try {
            Resource resource = resourceResolver.getResource(resourcePath);
            if (resource != null) {
                ModifiableValueMap modifiableValueMap = resource.adaptTo(ModifiableValueMap.class);
                if (modifiableValueMap != null) {
                    modifiableValueMap.put(propertyName, propertyValue);
                    resourceResolver.commit();
                    response.getWriter().write("Property added successfully!");
                } else {
                    response.getWriter().write("Failed to adapt resource to ModifiableValueMap.");
                }
            } else {
                response.getWriter().write("Resource not found.");
            }
        } catch (Exception e) {
            log.error("Error adding property to resource: {}", e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
