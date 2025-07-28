package com.alpha.core.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class ResourceTypeSevletTest {
    private final AemContext context=new AemContext();
    private ResourceTypeSevlet servlet =new ResourceTypeSevlet();

    @BeforeEach
    void setUp() {
        context.create().resource("/content/page","jcr:title","My Test Title");
        context.currentResource("/content/page");
        context.registerService(ResourceTypeSevlet.class,servlet);

    }

    @Test
    void testDoGet() throws ServletException, IOException {
        context.request().setResource(context.currentResource());
        servlet.doGet(context.request(),context.response());
        String output=context.response().getOutputAsString();
        assertTrue(output.contains("My Test Title"));
    }
}
