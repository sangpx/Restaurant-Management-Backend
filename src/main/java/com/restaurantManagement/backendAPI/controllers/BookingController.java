package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/customerAddBooking")
    public ResponseEntity<BookingDTO> customerAddBooking(
            @RequestBody BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.customerAddBooking(bookingDTO),
                HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PutMapping("/holdingDeskCustomer")
    public ResponseEntity<BookingDTO> holdingDeskCustomer(
            @RequestParam Long bookingId, @RequestParam Long deskId) {
        return new ResponseEntity<>(bookingService.holdingDeskCustomer(bookingId, deskId),
                HttpStatus.OK);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PutMapping("/confirmDeskCustomer")
    public ResponseEntity<BookingDTO> confirmDeskCustomer(
            @RequestParam Long bookingId) {
        return new ResponseEntity<>(bookingService.confirmDeskCustomer(bookingId),
                HttpStatus.OK);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PostMapping("/addBooking")
    public ResponseEntity<BookingDTO> addBooking(
            @RequestBody BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.addBooking(bookingDTO),
                HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PostMapping("/addMultipleBookings")
    public ResponseEntity<List<BookingDTO>> addMultipleBookings(
            @RequestBody List<BookingDTO> bookingDTOs) {
        return new ResponseEntity<>(bookingService.addMultipleBookings(bookingDTOs),
                HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
  @PreAuthorize("hasRole(('ADMIN'))")
    @PutMapping("/updateBooking/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @RequestBody BookingDTO bookingDTO, @PathVariable Long id) {
        return new ResponseEntity<>(bookingService.updateBooking(bookingDTO, id), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getAlls")
    public ResponseEntity<List<BookingDTO>> getAlls() {
        return ResponseEntity.ok(bookingService.getAlls());
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getDetailBooking/{id}")
    public ResponseEntity<BookingDTO> getDetailBooking(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookingService.getDetail(id));
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Long id){
        bookingService.delete(id);
        return ResponseEntity.ok("Xóa Đặt bàn thành công!");
    }
}
