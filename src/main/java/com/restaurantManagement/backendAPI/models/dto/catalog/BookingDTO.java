package com.restaurantManagement.backendAPI.models.dto.catalog;


import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long id;
//    private Date receivingTime;
    private String customerName;
    private String phone;
    private String address;
    private int quantityPerson;
//    @Enumerated(EnumType.STRING)
//    private EBookingStatus status;
    private Long deskId;
}
