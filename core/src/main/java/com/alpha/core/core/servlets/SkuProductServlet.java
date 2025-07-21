package com.alpha.core.core.servlets;


import com.alpha.core.core.utils.ResolverUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.day.cq.dam.api.Asset;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component(service= Servlet.class,
        property = {
                "sling.servlet.resourceTypes=alpha/components/productcard",
                "sling.servlet.methods=GET",
                "sling.servlet.selectors=product",
                "sling.servlet.extensions=json"
        }
)
public class SkuProductServlet  extends SlingSafeMethodsServlet{
    private static final String DAM_PATH="/content/dam/alpha/catalog/Products.json";
    private volatile JsonArray cacheData;
    private volatile long lastModified=0;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        Resource resource= request.getResource();
        String sku=resource.getValueMap().get("sku",String.class);
        if(sku==null||sku.trim().isEmpty()){
            response.sendError(400,"sku is not selected");
            return;
        }
        JsonArray catlog=loadCatlog();
        JsonObject product=null;
        for(JsonElement el:catlog){
            JsonObject obj=el.getAsJsonObject();
            if(sku.equalsIgnoreCase(obj.get("sku").getAsString())){
                product=obj;
            }
        }
        response.setContentType("application/json");
        response.getWriter().write(product.toString());

    }
    private JsonArray loadCatlog(){
        try{
            ResourceResolver resourceResolver=ResolverUtil.newResolver(resourceResolverFactory);
            Resource damResource=resourceResolver.getResource(DAM_PATH);
            Asset asset=(damResource!=null)?damResource.adaptTo(Asset.class):null;
            if(asset!=null){
                long modified=asset.getLastModified();
                if(cacheData==null||modified>lastModified){
                    try(InputStream is=asset.getOriginal().getStream()){
                        cacheData= JsonParser.parseReader(new InputStreamReader(is)).getAsJsonArray();
                        lastModified=modified;
                    }catch (IOException e) {
                        e.printStackTrace(); // or log this
                    }
                }

            }

        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return cacheData != null ? cacheData : new JsonArray();

    }

}
