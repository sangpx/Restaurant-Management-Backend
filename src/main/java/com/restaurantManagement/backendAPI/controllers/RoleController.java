package com.restaurantManagement.backendAPI.controllers;


import com.restaurantManagement.backendAPI.exceptions.RoleAlreadyExistException;
import com.restaurantManagement.backendAPI.models.dto.payload.response.MessageResponse;
import com.restaurantManagement.backendAPI.models.entity.Role;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
         return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK) ;
    }

    @PostMapping("/createRole")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteRole/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public ResponseEntity<MessageResponse> removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        Role role = roleService.removeAllUserFromRole(roleId);
        return ResponseEntity.ok(new MessageResponse("Remove All User From Role Success!", true));
    }

    @PostMapping("/remove-user-from-role")
    public ResponseEntity<MessageResponse> removeUserFromRole(@RequestParam("userId")Long userId,
                                   @RequestParam("roleId") Long roleId){
        User user = roleService.removeUserFromRole(userId, roleId);
        return ResponseEntity.ok(new MessageResponse("Remove User From Role Success!", true));
    }

    @PostMapping("/assign-user-to-role")
    public ResponseEntity<MessageResponse> assignUserToRole(@RequestParam("userId")Long userId,
                                                            @RequestParam("roleId") Long roleId){
        User user = roleService.assignUserToRole(userId, roleId);
        return ResponseEntity.ok(new MessageResponse("Assign Role Success!", true));
    }
}
