package com.kce.weather.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kce.weather.entity.WeatherRecord;

public interface WeatherRepository extends JpaRepository<WeatherRecord,Long>{
	List<WeatherRecord> findByDate(LocalDate date);
	@Query("SELECT w FROM WeatherRecord w WHERE YEAR(w.date) = :year")
    List<WeatherRecord> findByYear(@Param("year") int year);

    @Query("SELECT w FROM WeatherRecord w WHERE MONTH(w.date) = :month")
    List<WeatherRecord> findByMonth(@Param("month") int month);
    @Query("SELECT MIN(w.temperature) FROM WeatherRecord w WHERE YEAR(w.date) = :year AND MONTH(w.date) = :month")
    Double findMinTemp(int year, int month);
    @Query("SELECT MAX(w.temperature) FROM WeatherRecord w WHERE YEAR(w.date) = :year AND MONTH(w.date) = :month")
    Double findMaxTemp(int year, int month);
    @Query("SELECT AVG(w.temperature) FROM WeatherRecord w WHERE YEAR(w.date) = :year AND MONTH(w.date) = :month")
    Double findMedianTemp(int year, int month);
    
}
