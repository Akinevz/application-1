package com.akinevz.demo1.controller.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * A service for providing simple fetch-like access to JSON API
 */
public interface HttpConnectionService {

    /**
     * @param url             base url to connect to
     * @param queryParameters a map of query arguments
     * @throws IOException        thrown if a connection cannot be established
     * @throws URISyntaxException thrown if invalid URL string is provided
     */
    void connect(String url, Map<String, String> queryParameters) throws IOException, URISyntaxException;

    /**
     * @return an inputstream of the resource
     * @throws IOException if an I/O error occurs during reading
     */
    InputStream read() throws IOException;
}
