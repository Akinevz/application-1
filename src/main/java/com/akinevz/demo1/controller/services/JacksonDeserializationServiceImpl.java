package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReadings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class JacksonDeserializationServiceImpl implements JsonDeserializationService {
    final ObjectMapper objectMapper;

    @Autowired
    public JacksonDeserializationServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public DailyReadings deserialize(InputStream input) throws IOException {
        // get a reader for the root object
        var reader = objectMapper.readerFor(DailyReadings.class);
        return getDailyReadings(reader, input);
    }

    /**
     * Utility forwarder to the parser
     * @param reader a configured object reader for the Daily Readings class
     * @param input input stream representing the JSON data
     * @return a newly constructed representation of the JSON
     * @throws IOException if an error is encountered when reading from the stream
     */
    private DailyReadings getDailyReadings(ObjectReader reader, InputStream input) throws IOException {
        return reader.readValue(new InputStreamReader(new BufferedInputStream(input)));
    }
}
