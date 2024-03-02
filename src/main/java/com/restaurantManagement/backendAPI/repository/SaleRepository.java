package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
