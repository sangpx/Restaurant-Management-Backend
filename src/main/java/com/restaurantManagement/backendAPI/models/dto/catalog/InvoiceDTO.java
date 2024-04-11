package com.restaurantManagement.backendAPI.models.dto.catalog;

import com.restaurantManagement.backendAPI.models.entity.enums.EInvoiceStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private Date date;
    private Double totalPrice;
//    private String methodPay;
    @Enumerated(EnumType.STRING)
    private EInvoiceStatus status;
    private Date checkInTime;
    private Date checkOutTime;
    private Date createdAt;
    private Date updatedAt;
    private Long deskId;
    private Long bookingId;
}
