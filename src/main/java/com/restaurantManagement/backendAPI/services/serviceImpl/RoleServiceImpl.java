package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.RoleAlreadyExistException;
import com.restaurantManagement.backendAPI.exceptions.UserAlreadyExistException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.entity.enums.ERole;
import com.restaurantManagement.backendAPI.models.entity.Role;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.repository.RoleRepository;
import com.restaurantManagement.backendAPI.repository.UserRepository;
import com.restaurantManagement.backendAPI.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Đánh dấu một Class là tầng Service, phục vụ các logic nghiệp vụ.

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

//    @Override
//    public Optional<Role> findByRoleName(String roleName) {
//        return roleRepository.findByName(roleName);
//    }
    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public Role createRole(Role role) {
        Optional<Role> checkRole = roleRepository.findByName(role.getName());
        if (checkRole.isPresent()) {
            throw new RoleAlreadyExistException(checkRole.get().getName() + "Role already exist!");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUserFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UserNotFoundException("User not found!");
    }

    @Override
    public User assignUserToRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new UserAlreadyExistException(
                    user.get().getUsername() + " is already assigned to the " + role.get().getName() + " role!");
        }
        role.ifPresent(theRole -> theRole.assignUserToRole(user.get()));
        roleRepository.save(role.get());
        return user.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());

    }
}
