package com.restaurantManagement.backendAPI.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/receptionist")
  @PreAuthorize("hasRole('ROLE_RECEPTIONIST') or hasRole('ROLE_ADMIN')")
  public String receptionistAccess() {
    return "Receptionist Content.";
  }

  @GetMapping("/cashier")
  @PreAuthorize("hasRole('ROLE_CASHIER') or hasRole('ROLE_ADMIN')")
  public String cashierAccess() {
    return "Cashier Content.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String adminAccess() {
    return "Admin Content.";
  }
}
