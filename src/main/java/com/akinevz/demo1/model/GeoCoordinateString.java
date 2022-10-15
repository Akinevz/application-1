package com.akinevz.demo1.model;

import java.util.NoSuchElementException;

public class GeoCoordinateString {

    final String source;

    public GeoCoordinateString(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "GeoCoordString{" +
                "source='" + source + '\'' +
                '}';
    }

    public GeoCoord parsed() {

        try {
            var parser = new GeoCoordinateParser(source);
            var descriptor = parser.descriptor().orElse("unnamed location");
            var lat = parser.lat().orElseThrow(() -> new NoSuchElementException("could not locate latitude"));
            var lon = parser.lon().orElseThrow(() -> new NoSuchElementException("could not locate longitude"));
            return new GeoCoord(descriptor, lat, lon);
        } catch (Exception e) {
            var parser = new GeoCoordinateParser(source);
            var lat = parser.lat().orElseThrow(() -> new NoSuchElementException("could not locate latitude"));
            var lon = parser.lon().orElseThrow(() -> new NoSuchElementException("could not locate longitude"));
            return new GeoCoord(null, lat, lon);
        }

    }
}
