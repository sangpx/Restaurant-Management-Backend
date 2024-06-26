package com.restaurantManagement.backendAPI.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime bookingTime;
    private String customerName;
    private String address;
    private String email;
    private int quantityPerson;
    private String phone;
    @Enumerated(EnumType.STRING)
    private EBookingStatus status;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk_id")
    @JsonIgnore
    private Desk desk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Invoice> invoiceList;
}
