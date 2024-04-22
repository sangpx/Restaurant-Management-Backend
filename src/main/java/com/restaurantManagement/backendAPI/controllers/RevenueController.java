package com.restaurantManagement.backendAPI.controllers;


import com.restaurantManagement.backendAPI.services.RevenueReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/revenues")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
//@PreAuthorize("hasRole('ADMIN')")
public class RevenueController {
    @Autowired
    private RevenueReportService revenueReportService;

    @GetMapping("/daily")
    public ResponseEntity<Double> getDailyRevenue(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Double dailyRevenue = revenueReportService.calculateRevenueByCheckoutDate(date);
        return ResponseEntity.ok(dailyRevenue);
    }

    @GetMapping("/monthly")
    public ResponseEntity<Double> getMonthlyRevenue(
            @RequestParam int year, @RequestParam int month) {
        Double monthlyRevenue = revenueReportService.calculateRevenueByCheckoutMonth(year, month);
        return ResponseEntity.ok(monthlyRevenue);
    }

    @GetMapping("/today")
    public ResponseEntity<Double> getTodayRevenue() {
        Date today = new Date();
        Double todayRevenue = revenueReportService.calculateRevenueByCheckoutDate(today);
        return ResponseEntity.ok(todayRevenue);
    }
}
