package com.restaurantManagement.backendAPI.models.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDTO {
    private Long invoiceId;
    private Long foodId;
    private int quantity;
}
