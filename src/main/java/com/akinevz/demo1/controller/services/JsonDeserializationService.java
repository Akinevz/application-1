package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReadings;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple service for parsing a {@link DailyReadings} object from an input stream
 */
public interface JsonDeserializationService {
    /**
     * Uses the Jackson library to construct an object representing the daily readings returned by the API
     * @param input stream representing the JSON data
     * @return a parsed representation of the API result
     * @throws IOException if an exception occured while reading the source
     */
    DailyReadings deserialize(InputStream input) throws IOException;
}
