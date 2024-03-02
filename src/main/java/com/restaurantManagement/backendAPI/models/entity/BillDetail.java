package com.restaurantManagement.backendAPI.models.entity;

import com.restaurantManagement.backendAPI.models.entity.keys.KeyBillDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill_details")
public class BillDetail {

    @EmbeddedId
    KeyBillDetail keys;
    private int quantity;
    private double price;
    private double amount;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "bill_id", insertable=false, updatable=false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "food_id", insertable=false, updatable=false)
    private Food food;
}
