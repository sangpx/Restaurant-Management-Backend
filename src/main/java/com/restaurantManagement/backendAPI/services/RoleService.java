package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.ERole;
import com.restaurantManagement.backendAPI.models.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
    Optional<Role> findByRoleName(ERole roleName);
}
