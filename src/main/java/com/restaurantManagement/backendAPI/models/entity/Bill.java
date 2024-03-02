package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double total;
    private String methodPay;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id", referencedColumnName = "id", insertable = true, updatable = true, nullable=false)
//    @JsonBackReference(value="bill-movement")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
//    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "bill", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<BillDetail> billDetails;

    @OneToMany(mappedBy = "bill", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<BillDesk> billDesks;
}
