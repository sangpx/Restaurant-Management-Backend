package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.UserDTO;
import com.restaurantManagement.backendAPI.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //Kiem tra UserName da ton tai chua
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String userName);
    User saveOrUpdate(User user);
    User add(User user);
    User update(User user, Long id);
    void delete(Long id);
    UserDTO getDetail(Long id);
    Page<UserDTO> getUsersWithPaginationAndSorting(int pageNumber, int pageSize, String filed);
    List<UserDTO> searchUsers(String query);
    List<UserDTO> getAlls();
    UserDetails getCurrentUser();
    long countUsers();
}
