package com.restaurantManagement.backendAPI.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.entity.enums.EDeskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "desks")
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @Enumerated(EnumType.STRING)
    private EDeskStatus status;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "desk", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookingList;
}

