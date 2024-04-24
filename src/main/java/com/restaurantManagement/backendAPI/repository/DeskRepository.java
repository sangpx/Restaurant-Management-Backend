package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.enums.EDeskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    @Query("select d from Desk d where " +
            "d.name LIKE concat('%', :query, '%')")
    List<Desk> searchDesks(@Param("query") String query);
    long count();
}
