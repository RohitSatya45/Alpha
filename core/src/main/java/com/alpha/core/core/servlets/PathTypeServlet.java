package com.alpha.core.core.servlets;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
@Component(service= Servlet.class)
@SlingServletPaths(
        value={"/apps/alpha"}
)
public class PathTypeServlet extends SlingAllMethodsServlet {
    private static final Logger log= LoggerFactory.getLogger(PathTypeServlet.class);
    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resourceResolver= request.getResourceResolver();
        Page page= resourceResolver.adaptTo(PageManager.class).getPage("/content/alpha/us/en");
        JsonArray jsonArray=new JsonArray();
        try{
            Iterator<Page> childPages=page.listChildren();
            while(childPages.hasNext()){
                Page childpage= childPages.next();
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty(childpage.getTitle(), childpage.getPath());
                jsonArray.add(jsonObject);
            }
        }catch(Exception e){
            log.info("\n Error Message",e.getMessage());
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonArray.toString());
    }
    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

    }
}
