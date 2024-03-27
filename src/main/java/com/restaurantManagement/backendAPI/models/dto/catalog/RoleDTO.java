package com.restaurantManagement.backendAPI.models.dto.catalog;
import com.restaurantManagement.backendAPI.models.entity.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private ERole name;
}
