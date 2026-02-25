package com.securin.weather.service;

import com.securin.weather.dto.MonthlyStatsDto;
import java.util.Map;

public interface AnalyticsService {
    Map<String, MonthlyStatsDto> getMonthlyTemperatureStats(int year);
}
