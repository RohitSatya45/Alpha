package com.alpha.core.core.servlets;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(AemContextExtension.class)
class PathTypeServletTest {
    private final AemContext context=new AemContext();
    private PathTypeServlet servlet=new PathTypeServlet();


    @BeforeEach
    void setUp() {
        Page rootPage= context.create().page("/content/alpha/us/en");
        context.create().page("/content/alpha/us/en/page1", "jcr:title", "Page One");
        context.create().page("/content/alpha/us/en/page2", "jcr:title", "Page Two");

        context.currentPage(rootPage);
        context.registerService(PathTypeServlet.class,servlet);
    }
    @Test
    void doGet() throws ServletException, IOException {
        context.request().setPathInfo("/apps/alpha");
        context.request().setMethod("GET");
        servlet.service(context.request(), context.response());
        String responseJson= context.response().getOutputAsString();
        assertTrue(responseJson.contains("Page One"));
        assertTrue(responseJson.contains("/content/alpha/us/en/page1"));
        assertTrue(responseJson.contains("Page Two"));
        assertTrue(responseJson.contains("/content/alpha/us/en/page2"));

    }
}