package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT bk FROM Booking bk WHERE " +
            "bk.customerName LIKE CONCAT('%', :query, '%') " +
            "OR bk.time = :time " +
            "OR bk.status = :status")
    List<Booking> searchBookings(String query, Date time, EBookingStatus status);

}
