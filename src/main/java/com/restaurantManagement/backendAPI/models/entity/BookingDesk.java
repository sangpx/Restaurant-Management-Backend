package com.restaurantManagement.backendAPI.models.entity;

import com.restaurantManagement.backendAPI.models.entity.keys.KeyBookingDesk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking_desks")
public class BookingDesk {
    @EmbeddedId
    KeyBookingDesk keys;

    @ManyToOne
    @JoinColumn(name = "booking_id", insertable=false, updatable=false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "desk_id", insertable=false, updatable=false)
    private Desk desk;

}
