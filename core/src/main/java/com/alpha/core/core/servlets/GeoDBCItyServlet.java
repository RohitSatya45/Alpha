package com.alpha.core.core.servlets;

import com.google.gson.*;
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
import java.util.ArrayList;
import java.util.List;

@Component(service= Servlet.class,
property={
        "sling.servlet.paths=/bin/getGeoCities",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=json"
})
public class GeoDBCItyServlet  extends SlingSafeMethodsServlet {
    private static final long serialVersionUID=2L;
    private static final String API_KEY="ea4ea7da53msh4c34c5c5707c585p10f118jsn246e859d2d6d";
    private static final String API_URL="https://wft-geo-db.p.rapidapi.com/v1/geo/cities?types=CITY&countryIds=IN&limit=10&sort=-population";
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        URL url=new URL(API_URL);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
        conn.setRequestProperty("X-RapidAPI-Host", "wft-geo-db.p.rapidapi.com");
        conn.setConnectTimeout(5000); // 5 sec
        conn.setReadTimeout(5000);
        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        List<String>cities =new ArrayList<String>();
        JsonObject json= JsonParser.parseReader(reader).getAsJsonObject();
        JsonArray dataArray=json.getAsJsonArray("data");
        for (JsonElement element : dataArray){
            String city=element.getAsJsonObject().get("city").getAsString();
            cities.add(city);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(cities));

    }
}
