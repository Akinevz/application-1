package com.akinevz.demo1.controller.services;

import com.akinevz.demo1.model.DailyReading;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

@Service
public class NumberServiceImpl implements NumberService {

    @Override
    public Comparator<DailyReading> hottestReading(Comparator<DailyReading> order, Comparator<DailyReading> fallback) {
        return order.thenComparing(fallback);
    }

    @Override
    public LocalDateTime timestampToUTC(long unixTime) {
        return Instant.ofEpochSecond(unixTime).atOffset(ZoneOffset.UTC).toLocalDateTime();
    }
}
