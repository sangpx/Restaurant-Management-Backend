package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.dto.catalog.DeskDTO;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.Floor;
import com.restaurantManagement.backendAPI.repository.DeskRepository;
import com.restaurantManagement.backendAPI.services.DeskService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Desk add(Desk desk, Long floorId) {
        Desk createdDesk = new Desk();
        createdDesk.setName(desk.getName());
        createdDesk.setQuantitySeat(desk.getQuantitySeat());
        createdDesk.setStatus(false);
        createdDesk.setCreatedAt(Date.from(Instant.now()));
        createdDesk.setUpdatedAt(Date.from(Instant.now()));
        Floor floor = new Floor();
        floor.setId(floorId);
        createdDesk.setFloor(floor);
        return deskRepository.save(createdDesk);
    }

    @Override
    public Desk update(Desk desk, Long floorId, Long deskId) {
        Desk deskExist = deskRepository.findById(deskId)
                .orElseThrow(() -> new EntityNotFoundException("Not found Desk with: " + deskId));
        deskExist.setName(desk.getName());
        // Đảo ngược giá trị của status
        deskExist.setStatus(!deskExist.isStatus());
        deskExist.setQuantitySeat(desk.getQuantitySeat());
        deskExist.setUpdatedAt(Date.from(Instant.now()));
        Floor floor = new Floor();
        floor.setId(floorId);
        deskExist.setFloor(floor);
        return deskRepository.save(deskExist);
    }

    @Override
    public void delete(Long id) {
        Desk deskExist = deskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Desk with: " + id));
        deskRepository.delete(deskExist);
    }

    @Override
    public Desk getDetail(Long id) {
        return deskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Desk with: " + id));
    }

    @Override
    public List<Desk> searchDesk(String query) {
        List<Desk> desks = deskRepository.searchDesks(query);
        return desks;
    }

    @Override
    public List<DeskDTO> getAlls() {
        List<Desk> deskList = deskRepository.findAll();
        List<DeskDTO> deskDTOList = new ArrayList<>();
        deskList.forEach(desk -> deskDTOList.add(desk.entityToDTO()));
        return deskDTOList;
    }

    @Override
    public Page<DeskDTO> getDesksWithPaginationAndSorting(int pageNumber, int pageSize, String filed) {
        //DTO -> Entity
        Page<Desk> deskPage = deskRepository.findAll(PageRequest.of(pageNumber, pageSize)
                .withSort(Sort.by(filed)));
        //Entity -> DTO
        Page<DeskDTO> deskDTOPage = deskPage.map(desk ->
                modelMapper.map(desk, DeskDTO.class));
        return deskDTOPage;
    }
}