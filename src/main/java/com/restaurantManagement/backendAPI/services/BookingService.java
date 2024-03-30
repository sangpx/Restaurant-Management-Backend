package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingDTO add(BookingDTO bookingDTO, Long deskId, Long userId);
    List<BookingDTO> addMultipleBookings(List<BookingDTO> bookingDTOs, Long userId);
    Booking update(Booking booking, Long bookingId);
    void delete(Long id);
    Booking getDetail(Long id);
    List<Booking> searchBooking(String query);
    List<Booking> getAlls();
}
