package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.repository.InvoiceRepository;
import com.restaurantManagement.backendAPI.services.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RevenueReportServiceImpl implements RevenueReportService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Double calculateRevenueByCheckoutDate(Date date) {
        // Truy vấn cơ sở dữ liệu để tính toán tổng doanh thu theo ngày thanh toán
        Double totalRevenue = invoiceRepository.calculateTotalRevenueByCheckoutDate(date);
        return totalRevenue != null ? totalRevenue : 0.0;
    }

    @Override
    public Double calculateRevenueByCheckoutMonth(int year, int month) {
        // Truy vấn cơ sở dữ liệu để tính toán tổng doanh thu theo tháng thanh toán
        Double totalRevenue = invoiceRepository.calculateTotalRevenueByCheckoutMonth(year, month);
        return totalRevenue != null ? totalRevenue : 0.0;
    }
}
