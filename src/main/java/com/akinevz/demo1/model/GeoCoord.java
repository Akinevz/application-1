package com.akinevz.demo1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoCoord {
    String desc;
    String lat, lon;

    public GeoCoord(String desc, String lat, String lon) {
        this.desc = desc;
        this.lat = lat;
        this.lon = lon;
    }

}
