package com.restaurantManagement.backendAPI.models.dto.catalog;

import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private String bookingTime; // Sử dụng kiểu String thay vì LocalDateTime
    private String customerName;
    private String phone;
    private String address;
    private String email;
    private int quantityPerson;
    private EBookingStatus status;
    private Long deskId;

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    // Thêm setter method để chuyển đổi chuỗi thành LocalDateTime
    public void setBookingTimeFromString(String bookingTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(bookingTimeString, formatter);
        this.bookingTime = dateTime.toString(); // Lưu LocalDateTime dưới dạng chuỗi (optional)
    }
}
