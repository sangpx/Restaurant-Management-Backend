package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Double price;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(mappedBy = "food")
    @JsonIgnore
    private List<IngredientFood> ingredientFoodList;

    @OneToMany(mappedBy = "food")
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetailList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    public FoodDTO entityToDTO() {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(this.id);
        foodDTO.setName(this.name);
        foodDTO.setDescription(this.description);
        foodDTO.setPrice(this.price);
        if (this.category != null) {
            foodDTO.setCategoryId(this.category.getId());
            foodDTO.setCategoryName(this.category.getName());
        }
        return foodDTO;
    }
}
