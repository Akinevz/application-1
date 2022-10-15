package com.akinevz.demo1.controller.exceptions;

import java.io.IOException;

public class FailedToFetchWeatherInformationException extends Exception {
    public FailedToFetchWeatherInformationException(Exception e) {
        super(e);
    }
}
