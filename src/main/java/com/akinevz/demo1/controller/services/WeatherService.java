package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReading;
import com.akinevz.demo1.model.GeoCoord;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Service that returns the weather information
 */
public interface WeatherService {
    /**
     * Produces a list of readings from the API
     * @param geo a tagged geo coordinate in the format [label: lat, lon]
     * @return daily weather information
     * @throws IOException if a connection to the API can't be established
     * @throws URISyntaxException if a connection URL was specified incorrectly
     */
    List<DailyReading> weatherInformation(GeoCoord geo) throws IOException, URISyntaxException;
}
