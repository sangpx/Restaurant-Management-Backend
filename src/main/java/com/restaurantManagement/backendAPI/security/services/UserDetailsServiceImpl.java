package com.restaurantManagement.backendAPI.security.services;

import com.restaurantManagement.backendAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantManagement.backendAPI.models.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserService userService;

  /*
  phương thức load Người dùng theo tên người dùng và
  trả về một đối tượng UserDetails  mà Spring Security có thể sử dụng để xác thực
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

}
