package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.entity.enums.EInvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double totalPrice;
    private String methodPay;
    @Enumerated(EnumType.STRING)
    private EInvoiceStatus status;
    // Giờ vào
    private Date checkInTime;
    // Giờ ra
    private Date checkOutTime;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetailList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
