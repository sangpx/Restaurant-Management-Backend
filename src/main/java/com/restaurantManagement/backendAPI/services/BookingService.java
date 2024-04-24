package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import java.util.List;

public interface BookingService {

    BookingDTO customerAddBooking(BookingDTO bookingDTO);
    BookingDTO addBooking(BookingDTO bookingDTO);
    List<BookingDTO> addMultipleBookings(List<BookingDTO> bookingDTOs);
    BookingDTO updateBooking(BookingDTO bookingDTO, Long bookingId);
    BookingDTO holdingDeskCustomer(Long bookingId, Long deskId);
    BookingDTO confirmDeskCustomer(Long bookingId);
    void delete(Long bookingId);
    BookingDTO getDetail( Long bookingId);
    List<BookingDTO> getAlls();

    long countBookings();
}
