package com.restaurantManagement.backendAPI.repository;

import java.util.List;
import java.util.Optional;

import com.restaurantManagement.backendAPI.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
  @Query("select u from User u where " +
          "u.username LIKE concat('%', :query, '%')" +
          "or u.email like concat('%', :query, '%')" +
          "or u.phone like concat('%', :query, '%')")
  List<User> searchUsers(String query);
  long count();
}
