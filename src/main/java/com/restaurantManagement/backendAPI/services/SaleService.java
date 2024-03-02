package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Sale;
import java.util.List;

public interface SaleService {
    Sale add(Sale sale);
    Sale update(Sale sale, Long id);
    void delete(Long id);
    Sale getDetail(Long id);
    List<Sale> getAll();
}
