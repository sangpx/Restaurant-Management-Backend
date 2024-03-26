package com.restaurantManagement.backendAPI.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.entity.keys.KeyInvoiceDetail;
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
@Table(name = "invoice_detail")
public class InvoiceDetail {
    @EmbeddedId
    KeyInvoiceDetail keyInvoiceDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", insertable=false, updatable=false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", insertable=false, updatable=false)
    @JsonIgnore
    private Food food;

    private int quantity;
    private Double intoMoney;
    private Date createdAt;
    private Date updatedAt;
}
