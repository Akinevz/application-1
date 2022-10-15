package com.akinevz.demo1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DailyReadings {
    long lat, lon;
    String timezone;
    int timezoneOffset;
    List<DailyReading> daily;
}
