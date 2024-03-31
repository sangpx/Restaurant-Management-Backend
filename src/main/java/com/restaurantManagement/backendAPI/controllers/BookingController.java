package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.models.entity.Category;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import com.restaurantManagement.backendAPI.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/addMultipleBookings")
    public ResponseEntity<List<BookingDTO>> addMultipleBookings(
            @RequestBody List<BookingDTO> bookingDTOs, @RequestParam EBookingStatus bookingStatus
    ) {
        return new ResponseEntity<>(bookingService.addMultipleBookings(bookingDTOs, bookingStatus),
                HttpStatus.CREATED);
    }

    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @RequestBody BookingDTO bookingDTO, @PathVariable Long id) {
        return new ResponseEntity<>(bookingService.update(bookingDTO, id), HttpStatus.OK);
    }

    @GetMapping("/getAlls")
    public ResponseEntity<List<BookingDTO>> getAlls() {
        return ResponseEntity.ok(bookingService.getAlls());
    }

    @GetMapping("/getDetailBooking/{id}")
    public ResponseEntity<BookingDTO> getDetailBooking(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookingService.getDetail(id));
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id){
        bookingService.delete(id);
        return ResponseEntity.ok("Xóa Đặt bàn thành công!");
    }


}
