package com.restaurantManagement.backendAPI.models.dto.payload.response;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T>{
    int recordCount;
    T dataResponse;
}