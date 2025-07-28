package com.alpha.core.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityDataPathServletTest {
    private CityDataPathServlet servlet;
    @Mock
    SlingHttpServletRequest request;
    @Mock
    SlingHttpServletResponse response;
    private StringWriter responseWriter;
    @BeforeEach
    void setUp() throws IOException {
        responseWriter=new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }
    @Test
    void testDoGet_withCityParams() throws IOException, ServletException {
        when(request.getParameter("city")).thenReturn("london");
        servlet=spy(new CityDataPathServlet());
        doReturn("{\"temp\":25}").when(servlet).fetchWeatherJson("london");
        servlet.doGet(request,response);
        String output=responseWriter.toString();
        assertTrue(output.contains("\"temp\":25"));

    }
    @Test
    void testDoGet_withDefaultCity() throws Exception {
        when(request.getParameter("city")).thenReturn(null);

        servlet = spy(new CityDataPathServlet());
        doReturn("{\"temp\":30}").when(servlet).fetchWeatherJson("Hyderabad");

        servlet.doGet(request, response);

        String output = responseWriter.toString();
        assertTrue(output.contains("\"temp\":30"));
    }
}