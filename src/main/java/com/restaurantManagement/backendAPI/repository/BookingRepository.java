package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
