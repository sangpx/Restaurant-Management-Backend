package com.restaurantManagement.backendAPI.security.jwt;

import java.io.IOException;

import com.restaurantManagement.backendAPI.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/*
OncePerRequestFilterthực hiện một lần thực thi duy nhất cho mỗi yêu cầu đối với API.
 Nó cung cấp một phương thức doFilterInternal() mà chúng ta sẽ triển khai
 phân tích cú pháp và xác thực JWT,
 tải thông tin chi tiết về Người dùng (bằng cách sử dụng UserDetailsService),
 kiểm tra Ủy quyền (bằng cách sử dụng UsernamePasswordAuthenticationToken).
 */

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    //Kiem tra xem header Authorization co chua thong tin JWT khong
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      //Lay JWT tu Request
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        //Lay userName tu chuoi JWT
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        //Lay thong tin nguoi dung tu username
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //Neu nguoi dung hop le -> set thong tin cho Security Context
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication!", e);
    }

    filterChain.doFilter(request, response);
  }
}
