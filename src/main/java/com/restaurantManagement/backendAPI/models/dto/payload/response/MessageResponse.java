package com.restaurantManagement.backendAPI.models.dto.payload.response;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
  private String message;
  private boolean status;
}