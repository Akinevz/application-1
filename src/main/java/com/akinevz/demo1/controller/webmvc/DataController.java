package com.akinevz.demo1.controller.webmvc;

import com.akinevz.demo1.controller.exceptions.FailedToFetchWeatherInformationException;
import com.akinevz.demo1.controller.services.NumberService;
import com.akinevz.demo1.controller.services.WeatherService;
import com.akinevz.demo1.model.DailyReading;
import com.akinevz.demo1.model.GeoCoord;
import com.akinevz.demo1.model.GeoCoordinateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
public class DataController {

    final WeatherService weatherService;
    final NumberService numberService;

    @Autowired
    public DataController(WeatherService weatherService, NumberService numberService) {
        this.weatherService = weatherService;
        this.numberService = numberService;
    }

    @GetMapping("/forecast")
    @ResponseBody
    public String forecast(@RequestParam GeoCoordinateString info) throws FailedToFetchWeatherInformationException, URISyntaxException {

        // we need to find the hottest day

        // we start off with comparators. first find the hottest day, if there are multiple, ensure the least humid one
        var byTemperature = Comparator.comparing((DailyReading dr) -> dr.getTemp().getMax());
        var byHumidity = Comparator.comparing(DailyReading::getHumidity);
        Comparator<DailyReading> comparator = numberService.hottestReading(byTemperature, byHumidity.reversed());

        // we would like to return the date in a format that specifies date and day of the week at minimum
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        Function<LocalDateTime, String> formatDay =
                (LocalDateTime day) -> day.format(dateFormatter);
        Function<DailyReading, LocalDateTime> extractDay =
                (DailyReading dailyReading) -> numberService.timestampToUTC(dailyReading.getDt());

        // convert the request argument into a POJO representation
        GeoCoord geo = info.parsed();
        List<DailyReading> forecast;

        // use the API to acquire the weather information
        try {
            forecast = weatherService.weatherInformation(geo);
        } catch (IOException e) {
            throw new FailedToFetchWeatherInformationException(e);
        }

        // extract the hottest day recorded
        // nb: api appears to be returning 8 elements
        var sevenDays = forecast.stream().limit(7);
        return sevenDays.max(comparator)
                .map(extractDay)
                .map(formatDay)
                .orElseThrow(NoSuchElementException::new);
    }


}
