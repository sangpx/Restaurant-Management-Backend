package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/createBooking")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO,
                                                    @RequestParam("deskId") Long deskId ,
                                                    @RequestParam("userId") Long userId){
        return new ResponseEntity<>(bookingService.add(bookingDTO, deskId, userId), HttpStatus.CREATED);
    }
}
