package com.akinevz.demo1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyReading {
    long dt;


    long sunrise, sunset;

    long moonrise, moonset;
    double moonPhase;

    TempReading temp;

    FeelsLikeReading feelsLike;

    int pressure, humidity;
    double dewPoint;
    double windSpeed, windDeg, windGust;

    List<WeatherReading> weather;

    int clouds;
    double pop, rain, uvi;
}
