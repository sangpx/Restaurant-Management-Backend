package com.restaurantManagement.backendAPI.models.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.RoleDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 20)
    @Column(name = "user_name")
    private String username;
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;
    @NotNull
    @Size(max = 120)
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private boolean status;
    private Date createdAt;
    private Date updatedAt;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ImportCoupon> importCouponList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Booking> bookingList;
}
