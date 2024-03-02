package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.DeskDTO;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DeskService {
    Desk add(Desk desk, Long floorId);
    Desk update(Desk desk, Long floorId, Long deskId);
    void delete(Long id);
    DeskDTO getDetail(Long id);
    List<Desk> searchDesk(String query);
    Page<DeskDTO> getDesksWithPaginationAndSorting(int pageNumber, int pageSize, String filed);
    List<DeskDTO> getAlls();
}
