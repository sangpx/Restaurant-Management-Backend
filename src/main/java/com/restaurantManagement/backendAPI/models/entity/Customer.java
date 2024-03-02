package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    @Column(name = "time_arrived")
    private String timeArrived;
    private String note;
    @Column(name = "quantity_person")
    private int quantityPerson;
    private boolean status;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "accumulated_points")
    private int accumulatedPoints;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Bill> bill;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Booking> bookings;
}
