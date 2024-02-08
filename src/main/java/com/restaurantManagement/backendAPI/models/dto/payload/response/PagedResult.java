package com.restaurantManagement.backendAPI.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T> {
    int recordCount;
    T  response;
}
