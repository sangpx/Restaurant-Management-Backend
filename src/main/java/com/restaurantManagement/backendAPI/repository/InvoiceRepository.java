package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    //Tổng doanh thu theo ngày
    @Query("SELECT SUM(i.totalPrice) FROM Invoice i WHERE DATE(i.checkOutTime) = :date")
    Double calculateTotalRevenueByCheckoutDate(@Param("date") Date date);

    //Tổng doanh thu theo tháng
    @Query("SELECT SUM(i.totalPrice) FROM Invoice i WHERE" +
            " YEAR(i.checkOutTime) = :year AND MONTH(i.checkOutTime) = :month")
    Double calculateTotalRevenueByCheckoutMonth(@Param("year") int year, @Param("month") int month);

    //Tổng số hóa đơn theo ngày
    @Query("SELECT COUNT(i) FROM Invoice i WHERE DATE(i.date) = :date")
    Long countInvoicesByDate(@Param("date") Date date);

    //Tổng số hóa đơn theo tháng
    @Query("SELECT COUNT(i) FROM Invoice i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    Long countInvoicesByMonth(@Param("year") int year, @Param("month") int month);

    Invoice findByBookingId(Long bookingId);

}
