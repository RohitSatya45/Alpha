package com.alpha.core.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component(service= Servlet.class,
property={
        "sling.servlet.paths=/bin/getCityDetaials",
        "sling.servlet.methods=get",
        "sling.servlet.extensions=json"
})

public class CityDataPathServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "526f5fa3eb13601de851b5876138b212";
    private static final String API_URL_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        if (city == null || city.trim().isEmpty()) {
            city = "Hyderabad";
        }
        String encodeCity = URLEncoder.encode(city, "UTF-8");
        String apiURL = String.format(API_URL_TEMPLATE, encodeCity, API_KEY);
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(sb.toString());
    }
}
