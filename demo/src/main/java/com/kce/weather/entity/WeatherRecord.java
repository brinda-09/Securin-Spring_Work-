package com.kce.weather.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="weather")
public class WeatherRecord {
	@Id
	private Long id;
	private LocalDate date;
    private double temperature;
    private double humidity;
    private double pressure;
    @Column(name = "weather_condition")
    private String condition;
    
	

	public WeatherRecord() {
		super();
	}
	public WeatherRecord(Long id, LocalDate date, double temperature, double humidity, double pressure,
			String condition) {
		super();
		this.id = id;
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.condition = condition;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
    
}
