package com.example.demo.tools.datetime.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherTools {

    private static final String BASE_URL="https://api.weather.gov";
    private final RestClient restClient;


    public WeatherTools(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/geo=json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Points(@JsonProperty("properties") Props properties){
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Props(@JsonProperty("forecast") String forecast){

        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Forecast(@JsonProperty("properties") Props properties){
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Props(@JsonProperty("periods") String forecast){

        }

    }

}
