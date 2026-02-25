package com.kce.weather.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kce.weather.entity.WeatherRecord;
import com.kce.weather.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	@Autowired
	private WeatherService weatherservice;
	@PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Upload CSV file")
	
	public ResponseEntity<?> uploadCSV(@RequestPart("file") MultipartFile file) throws Exception {
	    return ResponseEntity.ok(weatherservice.uploadProducts(file));
	}
    @GetMapping("/month/{month}")
    public ResponseEntity<List<WeatherRecord>> getByMonth(@PathVariable int month) {
        return ResponseEntity.ok(weatherservice.getWeatherByMonth(month));
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<WeatherRecord>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(weatherservice.getWeatherByDate(LocalDate.parse(date)));
    }
    @GetMapping("/min/{year}/{month}")
    public ResponseEntity<Double> getMinTemp(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(weatherservice.getMinTempForMonth(year, month));
    }
    @GetMapping("/max/{year}/{month}")
    public ResponseEntity<Double> getMaxTemp(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(weatherservice.getMaxTempForMonth(year, month));
    }
    @GetMapping("/median/{year}/{month}")
    public ResponseEntity<Double> getMedianTemp(
            @PathVariable int year,
            @PathVariable int month) {
        return ResponseEntity.ok(weatherservice.getMedianTempForMonth(year, month));
    }
}
