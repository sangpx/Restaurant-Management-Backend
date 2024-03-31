package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.exceptions.RoleAlreadyExistException;
import com.restaurantManagement.backendAPI.models.entity.enums.ERole;
import com.restaurantManagement.backendAPI.models.entity.Role;
import com.restaurantManagement.backendAPI.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAllRoles();
//    Optional<Role> findByRoleName(String roleName);
    Optional<Role> findByRoleName(ERole roleName);

    Role findById(Long roleId);

    Role createRole(Role role);

    void deleteRole(Long roleId);

    User removeUserFromRole(Long userId, Long roleId);

    User assignUserToRole(Long userId, Long roleId);

    Role removeAllUserFromRole(Long roleId);
}
