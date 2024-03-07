package com.restaurantManagement.backendAPI.models.dto.payload.request;

import java.util.Date;
import java.util.Set;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  private String username;
  @Email
  private String email;
  private String password;
  private Set<String> role;
  private String phone;
  private String gender;
  private boolean status;
  private Date createdAt;
  private Date updatedAt;
}
