package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;

import java.util.Date;
import java.util.List;

public interface BookingService {
    List<BookingDTO> addMultipleBookings(List<BookingDTO> bookingDTOs, EBookingStatus bookingStatus);
    BookingDTO update(BookingDTO bookingDTO, Long bookingId);
    void delete(Long bookingId);
    BookingDTO getDetail( Long bookingId);
    List<BookingDTO> searchBooking(String query, Date time, EBookingStatus status);
    List<BookingDTO> getAlls();
}
