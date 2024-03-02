package com.restaurantManagement.backendAPI.models.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyBookingDesk implements Serializable {
    @Column(name = "booking_id")
    private Long bookingId;
    @Column(name = "desk_id")
    private Long deskId;
}
