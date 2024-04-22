package com.restaurantManagement.backendAPI.services;

import java.util.Date;

public interface RevenueReportService {
    Double calculateRevenueByCheckoutDate(Date date);
    Double calculateRevenueByCheckoutMonth(int year, int month);
}
