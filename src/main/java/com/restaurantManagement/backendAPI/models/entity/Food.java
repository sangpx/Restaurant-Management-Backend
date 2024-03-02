package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.dto.catalog.DeskDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.FoodDTO;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private String description;
    private String image;
    private Double price;
    private boolean status;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "food")
    @JsonIgnore
    private List<BillDetail> billDetails;

    public FoodDTO entityToDTO() {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(this.id);
        foodDTO.setName(this.name);
        foodDTO.setDescription(this.description);
        foodDTO.setImage(this.image);
        foodDTO.setStatus(this.status);
        foodDTO.setPrice(this.price);
        if (this.category != null) {
            foodDTO.setCategoryId(this.category.getId());
            foodDTO.setCategoryName(this.category.getName());
        }
        return foodDTO;
    }
}
