package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Desk;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeskService {
    Desk add(Desk desk);
    Desk update(Desk desk, Long deskId);
    void delete(Long id);
    Desk getDetail(Long id);
    List<Desk> searchDesk(String query);
    Page<Desk> getDesksWithPaginationAndSorting(int pageNumber, int pageSize, String filed);
    List<Desk> getAlls();
    long countDesks();
}
