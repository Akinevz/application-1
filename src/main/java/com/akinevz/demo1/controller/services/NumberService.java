package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReading;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * A service to manipulate the returned representation.
 * <p>
 * This object provides utilities to finding the most humid warmest day and converting between date representations
 */
public interface NumberService {

    /**
     * Utility method to create a comparator that finds the most humid warmest day
     * @param order primary sorting comparator - temperature
     * @param fallback sorting comparator for when two days are of the same temperature
     * @return a comparator
     */
    Comparator<DailyReading> hottestReading(Comparator<DailyReading> order, Comparator<DailyReading> fallback);

    /**
     * NB: API returns dt as unix seconds not unix millis
     *
     * @param unixTime time in seconds since unix epoch
     * @return a UTC-based date object
     */
    LocalDateTime timestampToUTC(long unixTime);

}
