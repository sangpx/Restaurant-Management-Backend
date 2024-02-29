package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Floor;
import com.restaurantManagement.backendAPI.repository.FloorRepository;
import com.restaurantManagement.backendAPI.services.FloorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@Service
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorRepository floorRepository;

    @Override
    public Floor add(Floor floor) {
        Floor createFloor = new Floor();
        createFloor.setName(floor.getName());
        createFloor.setCreatedAt(Date.from(Instant.now()));
        createFloor.setUpdatedAt(Date.from(Instant.now()));
        return floorRepository.save(createFloor);
    }
    @Override
    public Floor update(Floor floor, Long id) {
        Floor floorExist = floorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Floor with: " + id));
        floorExist.setName(floor.getName());
        floorExist.setUpdatedAt(Date.from(Instant.now()));
        return floorRepository.save(floorExist);
    }
    @Override
    public void delete(Long id) {
        Floor floorExist = floorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Floor with: " + id));
        floorRepository.delete(floorExist);
    }
    @Override
    public Floor getDetail(Long id) {
        return floorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Floor with: " + id));
    }
    @Override
    public List<Floor> getAll() {
        return floorRepository.findAll();
    }
}
