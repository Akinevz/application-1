package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReading;
import com.akinevz.demo1.model.GeoCoord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final HttpConnectionService httpConnectionService;
    private final JsonDeserializationService jsonService;

    private final String[] potentialKeys = new String[]{
            "3f159511ac64f5393feaa6f3c700c74b",
            "91a5e3d94708c57e8248a454d817a493",
            "F8bbd2c4154b67e214403406996bbf44",
            "44d972716a576f33e58ba3112e9a3cdd"
    };

    @Autowired
    public WeatherServiceImpl(HttpConnectionService httpConnectionService, JsonDeserializationService jsonService) {
        this.httpConnectionService = httpConnectionService;
        this.jsonService = jsonService;
    }

    @Override
    public List<DailyReading> weatherInformation(GeoCoord geo) throws URISyntaxException, IOException {
        // set parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("lat", geo.getLat());
        params.put("lon", geo.getLon());
        params.put("units", "metric");
        params.put("exclude", "current,minutely,hourly,alerts");
        params.put("appid", potentialKeys[0]);

        // create target URL
        String baseURL = "https://api.openweathermap.org/data/2.5/onecall";
        httpConnectionService.connect(baseURL, params);

        // perform reading using an input stream
        var inputStream = httpConnectionService.read();
        var response = jsonService.deserialize(inputStream);
        return response.getDaily();
    }
}
