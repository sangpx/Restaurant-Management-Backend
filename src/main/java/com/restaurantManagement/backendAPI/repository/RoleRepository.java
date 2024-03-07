package com.restaurantManagement.backendAPI.repository;

import java.util.Optional;

import com.restaurantManagement.backendAPI.models.entity.ERole;
import com.restaurantManagement.backendAPI.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

//  Optional<Role> findByName(String name);
    Optional<Role> findByName(ERole name);

}
