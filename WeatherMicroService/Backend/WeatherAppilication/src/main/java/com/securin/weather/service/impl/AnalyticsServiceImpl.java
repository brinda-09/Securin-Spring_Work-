package com.securin.weather.service.impl;

import com.securin.weather.dto.MonthlyStatsDto;
import com.securin.weather.entity.Weather;
import com.securin.weather.repository.WeatherRepository;
import com.securin.weather.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final WeatherRepository weatherRepository;

    @Override
    public Map<String, MonthlyStatsDto> getMonthlyTemperatureStats(int year) {
        List<Weather> yearlyData = weatherRepository.findByYear(year);

        // Group by month
        Map<Integer, List<Weather>> byMonth = yearlyData.stream()
                .filter(w -> w.getTemperature() != null)
                .collect(Collectors.groupingBy(w -> w.getRecordDate().getMonthValue()));

        Map<String, MonthlyStatsDto> stats = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            List<Weather> monthData = byMonth.getOrDefault(month, Collections.emptyList());
            if (monthData.isEmpty()) {
                continue;
            }

            List<Double> temps = monthData.stream()
                    .map(Weather::getTemperature)
                    .sorted()
                    .collect(Collectors.toList());

            double min = temps.get(0);
            double max = temps.get(temps.size() - 1);

            double median;
            int size = temps.size();
            if (size % 2 == 0) {
                median = (temps.get(size / 2 - 1) + temps.get(size / 2)) / 2.0;
            } else {
                median = temps.get(size / 2);
            }

            MonthlyStatsDto monthStats = MonthlyStatsDto.builder()
                    .high(max)
                    .median(median)
                    .minimum(min)
                    .build();

            stats.put("Month_" + month, monthStats);
        }

        return stats;
    }
}
