package com.akinevz.demo1.controller.services;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * {@inheritDoc}
 */
@Service
public class HttpConnectionServiceImpl implements HttpConnectionService {


    private HttpURLConnection connection;
    /*
    TODO: negotiate a sensible timeout
     */
    private final static int DEFAULT_TIMEOUT = 3600;
    private final static Charset CHARSET = Charset.defaultCharset();


    @Override
    public void connect(String url, Map<String, String> queryParameters) throws IOException, URISyntaxException {
        var builder = new URIBuilder(url);
        for (Map.Entry<String, String> kv : queryParameters.entrySet()) {
            builder.addParameter(kv.getKey(), kv.getValue());
        }
        var address = builder.build().toURL();

        this.connection = (HttpURLConnection) address.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", CHARSET.name());
        connection.setConnectTimeout(DEFAULT_TIMEOUT);
    }


    @Override
    public InputStream read() throws IOException {
        return this.connection.getInputStream();
    }
}
