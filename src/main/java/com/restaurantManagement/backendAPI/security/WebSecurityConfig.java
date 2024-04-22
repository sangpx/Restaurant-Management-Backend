package com.restaurantManagement.backendAPI.security;

import com.restaurantManagement.backendAPI.security.jwt.AuthEntryPointJwt;
import com.restaurantManagement.backendAPI.security.jwt.AuthTokenFilter;
import com.restaurantManagement.backendAPI.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
WebSecurityConfiglà mấu chốt trong việc triển khai bảo mật của chúng tôi.
 Nó cấu hình cors, csrf, quản lý phiên, quy tắc cho tài nguyên được bảo vệ.
*/

@Configuration
@EnableMethodSecurity

public class WebSecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

  /*
  AuthenticationManagercó DaoAuthenticationProvider(với sự trợ giúp của UserDetailsService &
  PasswordEncoder)
  để xác thực đối tượng UsernamePasswordAuthenticationToken. Nếu thành công,
  AuthenticationManager sẽ trả về một đối tượng Xác thực được điền đầy đủ (bao gồm cả các quyền được cấp).
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //Config SecurityFilterChain: là một phần quan trọng của cấu hình bảo mật trong Spring Security
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // disable csrf
            // tra ve exception khi xảy ra người dùng không xác thực
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            //không lưu trữ trạng thái phiên
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.requestMatchers("/api/auth/**").permitAll() // Cho phep tat ca moi nguoi truy cap
              .requestMatchers("/api/**").permitAll()
              .requestMatchers("api/bookings/**").permitAll()
              .requestMatchers("api/invoices/**").permitAll()
              .anyRequest().authenticated()
        );

    http.authenticationProvider(authenticationProvider());

    //Thêm Filter: Filter này được sử dụng để xử lý xác thực thông qua token (JWT).
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
