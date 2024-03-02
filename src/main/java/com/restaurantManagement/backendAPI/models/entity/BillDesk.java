package com.restaurantManagement.backendAPI.models.entity;


import com.restaurantManagement.backendAPI.models.entity.keys.KeyBillDesk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill_desks")
public class BillDesk {
    @EmbeddedId
    KeyBillDesk keys;

    @ManyToOne
    @JoinColumn(name = "bill_id", insertable=false, updatable=false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "desk_id", insertable=false, updatable=false)
    private Desk desk;
}
