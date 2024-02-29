package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Floor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FloorService {
    Floor add(Floor floor);
    Floor update(Floor floor, Long id);
    void delete(Long id);
    Floor getDetail(Long id);
    List<Floor> getAll();
}
