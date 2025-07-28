package com.alpha.core.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(AemContextExtension.class)
class ResourceTypePostServletTest {
    private final AemContext context=new AemContext();
    private ResourceTypePostServlet servlet=new ResourceTypePostServlet();
    @BeforeEach
    void setUp() {
        context.create().resource("/content/alpha", "jcr:title", "hello");
        context.request().setResource(context.resourceResolver().getResource("/content/alpha"));

        // ✅ Use addRequestParameter instead of setParameter or setParameterMap
        context.request().addRequestParameter("propertyName", "jcr:title");
        context.request().addRequestParameter("propertyValue", "hello");

        context.registerService(ResourceTypePostServlet.class, servlet);
    }


    @Test
    void doPost() throws ServletException, IOException {
        servlet.doPost(context.request(), context.response());
        String response=context.response().getOutputAsString();
        assertTrue(response.contains("Property added successfully"));
        Resource resource=context.resourceResolver().getResource("/content/alpha");
        ModifiableValueMap modifiableValueMap=resource.adaptTo(ModifiableValueMap.class);
        assertEquals("hello",modifiableValueMap.get("jcr:title"));

    }
}