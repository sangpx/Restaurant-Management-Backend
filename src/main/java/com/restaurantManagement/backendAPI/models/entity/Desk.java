package com.restaurantManagement.backendAPI.models.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.restaurantManagement.backendAPI.models.dto.catalog.DeskDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "desks")
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private boolean status;
    @Column(name = "quantity_seat")
    private int quantitySeat;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    @JsonBackReference
    private Floor floor;

    public DeskDTO entityToDTO() {
        DeskDTO deskDto = new DeskDTO();
        deskDto.setId(this.id);
        deskDto.setName(this.name);
        deskDto.setStatus(this.status);
        deskDto.setQuantitySeat(this.quantitySeat);
        if (this.floor != null) {
            deskDto.setFloorId(this.floor.getId());
            deskDto.setFloorName(this.floor.getName());
        }
        return deskDto;
    }
}
