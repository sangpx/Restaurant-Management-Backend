package com.restaurantManagement.backendAPI.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "import_coupon")
public class ImportCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date requestDate;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "importCoupon")
    @JsonIgnore
    private List<ImportDetail> importDetailList;
}
