package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public void removeAllUsersFromRole() {
      if(this.getUsers() != null) {
        List<User> usersInRole = this.getUsers().stream().toList();
        usersInRole.forEach(this::removeUserFromRole);
      }
    }

  public void removeUserFromRole(User user) {
      user.getRoles().remove(this);
      this.getUsers().remove(user);
  }

  public void assignUserToRole(User user) {
    user.getRoles().add(this);
    this.getUsers().add(user);
  }
}