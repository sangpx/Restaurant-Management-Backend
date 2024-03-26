package com.restaurantManagement.backendAPI.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurantManagement.backendAPI.models.entity.keys.KeyImportDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "import_detail")
public class ImportDetail {
    @EmbeddedId
    KeyImportDetail keyImportDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_id", insertable=false, updatable=false)
    @JsonIgnore
    private ImportCoupon importCoupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", insertable=false, updatable=false)
    @JsonIgnore
    private Ingredient ingredient;

    private int quantityRequest;
    private Double intoMoney;
    private Date createdAt;
    private Date updatedAt;
}
