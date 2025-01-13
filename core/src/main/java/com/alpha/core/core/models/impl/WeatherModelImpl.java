package com.alpha.core.core.models.impl;

import com.alpha.core.core.models.WeatherModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Model(
        adaptables = Resource.class,
        adapters = WeatherModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class WeatherModelImpl implements WeatherModel {
    private static final Logger log = LoggerFactory.getLogger(WeatherModel.class);

    @Inject
    Resource resource;

    @Inject
    String city;

    private String temperature;
    private String description;
    private String errorMessage;

    @PostConstruct
    protected void init() {
        if (city != null && !city.isEmpty()) {
            fetchWeatherData(city);
        } else {
            this.errorMessage = "City name is not provided.";
        }
    }

    private void fetchWeatherData(String city) {
        String apiKey = "526f5fa3eb13601de851b5876138b212";
        String apiUrl = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            this.temperature = jsonResponse.getAsJsonObject("main").get("temp").getAsString();
            this.description = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

            log.info("Weather data fetched successfully: {}°C, {}", temperature, description);
        } catch (Exception e) {
            log.error("Error while fetching weather data: {}", e.getMessage());
            this.errorMessage = "Failed to fetch weather data. Please try again later.";
        }
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getTemperature() {
        return temperature;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
