package com.alpha.core.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service= Servlet.class)
@SlingServletResourceTypes(
        resourceTypes="alpha/components/page",
        methods={HttpConstants.METHOD_POST,HttpConstants.METHOD_GET},
        selectors={"geeks","test"},
        extensions={"txt","xml"}
)
public class ResourceTypeSevlet extends SlingAllMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        Resource resource = request.getResource();
        response.setContentType("txt/plain");
        response.getWriter().write("Page Title = "+resource.getValueMap().get(JcrConstants.JCR_TITLE));
    }
    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}



