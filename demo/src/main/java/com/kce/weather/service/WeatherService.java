package com.kce.weather.service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kce.weather.entity.WeatherRecord;
import com.kce.weather.repository.WeatherRepository;
import com.opencsv.CSVReader;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherrepo;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");

    public String uploadProducts(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty!");
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new RuntimeException("Only CSV files are allowed!");
        }

        List<WeatherRecord> weatherList = new ArrayList<>();
        int skipped = 0;

        try (CSVReader reader =
                     new CSVReader(new InputStreamReader(file.getInputStream()))) {

            List<String[]> rows = reader.readAll();

            for (int i = 1; i < rows.size(); i++) {

                try {
                    String[] row = rows.get(i);

                    WeatherRecord weather = new WeatherRecord();

                    // ✅ Date parsing
                    LocalDate date = LocalDateTime
                            .parse(row[0].trim(), FORMATTER)
                            .toLocalDate();
                    weather.setDate(date);

                    // ✅ Correct column mapping
                    weather.setCondition(safeString(row[1]));
                    weather.setHumidity(parseDoubleSafe(row[6]));
                    weather.setPressure(parseDoubleSafe(row[8]));
                    weather.setTemperature(parseDoubleSafe(row[11]));

                    weatherList.add(weather);

                } catch (Exception e) {
                    skipped++;   // Skip invalid rows
                }
            }

            weatherrepo.saveAll(weatherList);
        }

        return "Upload Completed. Inserted: " + weatherList.size()
                + " | Skipped: " + skipped;
    }

    // ✅ Safe double parser
    private Double parseDoubleSafe(String value) {
        if (value == null) return null;

        value = value.trim();

        if (value.isEmpty() ||
                value.equalsIgnoreCase("N/A") ||
                value.equals("-9999")) {
            return null;
        }

        return Double.parseDouble(value);
    }

    // ✅ Safe string parser
    private String safeString(String value) {
        if (value == null) return null;
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

    // ==============================
    // OTHER SERVICE METHODS
    // ==============================

    public List<WeatherRecord> getWeatherByMonth(int month) {
        return weatherrepo.findByMonth(month);
    }

    public List<WeatherRecord> getWeatherByDate(LocalDate date) {
        return weatherrepo.findByDate(date);
    }

    public Double getMinTempForMonth(int year, int month) {
        return weatherrepo.findMinTemp(year, month);
    }

    public Double getMaxTempForMonth(int year, int month) {
        return weatherrepo.findMaxTemp(year, month);
    }

    public Double getMedianTempForMonth(int year, int month) {
        return weatherrepo.findMedianTemp(year, month);
    }

    public List<WeatherRecord> sortByTemperature(String order) {
        if (order.equalsIgnoreCase("desc")) {
            return weatherrepo.findAll(Sort.by("temperature").descending());
        }
        return weatherrepo.findAll(Sort.by("temperature").ascending());
    }

    public List<WeatherRecord> sortHumidityByMonth(int month, String order) {
        List<WeatherRecord> list = weatherrepo.findByMonth(month);

        if (order.equalsIgnoreCase("desc")) {
            list.sort(Comparator.comparing(WeatherRecord::getHumidity,
                    Comparator.nullsLast(Double::compareTo)).reversed());
        } else {
            list.sort(Comparator.comparing(WeatherRecord::getHumidity,
                    Comparator.nullsLast(Double::compareTo)));
        }

        return list;
    }
}