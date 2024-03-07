package com.restaurantManagement.backendAPI.controllers.systemManage;


import java.util.List;
import java.util.stream.Collectors;

import com.restaurantManagement.backendAPI.models.dto.catalog.UserDTO;
import com.restaurantManagement.backendAPI.models.dto.payload.request.SigninRequest;
import com.restaurantManagement.backendAPI.models.dto.payload.response.PageResult;
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

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")
  public ResponseEntity<MessageResponse> signUp(@RequestBody User user){
    User signUpUser = userService.add(user);
    return ResponseEntity.ok(new MessageResponse("SignUp Success!", true));
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
