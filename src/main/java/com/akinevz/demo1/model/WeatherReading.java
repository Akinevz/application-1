package com.akinevz.demo1.model;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class WeatherReading {
    int id;
    String main;
    String description;
    String icon;
}
