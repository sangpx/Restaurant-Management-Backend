package com.restaurantManagement.backendAPI.controllers;


import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.restaurantManagement.backendAPI.models.dto.catalog.UserDTO;
import com.restaurantManagement.backendAPI.models.dto.payload.request.SigninRequest;
import com.restaurantManagement.backendAPI.models.dto.payload.request.SignupRequest;
import com.restaurantManagement.backendAPI.models.dto.payload.response.PageResult;
import com.restaurantManagement.backendAPI.models.entity.enums.ERole;
import com.restaurantManagement.backendAPI.models.entity.Role;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.services.RoleService;
import com.restaurantManagement.backendAPI.services.UserService;

import com.restaurantManagement.backendAPI.security.jwt.JwtUtils;
import com.restaurantManagement.backendAPI.security.services.UserDetailsImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.restaurantManagement.backendAPI.models.dto.payload.response.JwtResponse;
import com.restaurantManagement.backendAPI.models.dto.payload.response.MessageResponse;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")

public class UserController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  RoleService roleService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@Valid @RequestBody SigninRequest siginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(siginRequest.getUsername(), siginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

//  @PreAuthorize("hasRole('ADMIN')")
//  @PostMapping("/signup")
//  public ResponseEntity<MessageResponse> signUp(@RequestBody User user){
//    User signUpUser = userService.add(user);
//    return ResponseEntity.ok(new MessageResponse("SignUp Success!", true));
//  }
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
    if (userService.existsByUsername(request.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!", false));
    }

    if (userService.existsByEmail(request.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!", false));
    }

    // Create new user's account
    User userCreate = new User();
    userCreate.setUsername(request.getUsername());
    userCreate.setPassword(encoder.encode(request.getPassword()));
    userCreate.setEmail(request.getEmail());
    userCreate.setPhone(request.getPhone());
    userCreate.setStatus(true);
    userCreate.setCreatedAt(Date.from(Instant.now()));
    userCreate.setUpdatedAt(Date.from(Instant.now()));
    Set<String> strRoles = request.getRole();
    Set<Role> roles = new HashSet<>();
    //Kiem tra Role co bi null khong
    if (strRoles == null) {
      Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      //Add Role mac dinh la User
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;

          case "receptionist":
            Role receptionistRole = roleService.findByRoleName(ERole.ROLE_RECEPTIONIST)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(receptionistRole);
            break;

          case "cashier":
            Role cashierRole = roleService.findByRoleName(ERole.ROLE_CASHIER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(cashierRole);
            break;

          default:
            Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }
    userCreate.setRoles(roles);
    userService.saveOrUpdate(userCreate);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!", true));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/getDetailUser/{id}")
  public UserDTO getDetailUser(@PathVariable("id") Long id){
    return userService.getDetail(id);
  }


  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/deleteUser/{id}")
  public void delete(@PathVariable("id") Long id){
    userService.delete(id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/updateUser/{id}")
  public ResponseEntity<MessageResponse> update(
          @RequestBody User user, @PathVariable Long id){
    User updateUser = userService.update(user, id);
    return ResponseEntity.ok(new MessageResponse("Updated Success!", true));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/getUserPaging")
  public PageResult<Page<UserDTO>> getUserPaging(
          @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String filed){
    Page<UserDTO> userDTOPages = userService.getUsersWithPaginationAndSorting(pageNumber, pageSize, filed);
    return new PageResult<>(userDTOPages.getSize(), userDTOPages);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/searchUsers")
  public ResponseEntity<List<UserDTO>> searchUsers(
          @RequestParam("query") String query){
    return ResponseEntity.ok(userService.searchUsers(query));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/getAlls")
  public ResponseEntity<List<UserDTO>> getAlls(){
    return ResponseEntity.ok(userService.getAlls());
  }
}
