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
public class KeyBillDetail implements Serializable {
    @Column(name = "bill_id")
    private Long billId;
    @Column(name = "food_id")
    private Long foodId;
}
