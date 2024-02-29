package com.restaurantManagement.backendAPI.controllers.systemManage;


import com.restaurantManagement.backendAPI.models.entity.Floor;
import com.restaurantManagement.backendAPI.services.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @GetMapping("/getAllFloors")
    public ResponseEntity<?> getAllFloors(){
        List<Floor> floorList = floorService.getAll();
        return ResponseEntity.ok(floorList);
    }

    @GetMapping("/getDetailFloor/{id}")
    public ResponseEntity<Floor> getDetailFloor(@PathVariable("id") Long id){
        Floor floor = floorService.getDetail(id);
        return ResponseEntity.ok(floor);
    }

    @PostMapping("/createFloor")
    public ResponseEntity<Floor> createFloor(@RequestBody Floor floor){
        Floor savedFloor = floorService.add(floor);
        return new ResponseEntity<>(savedFloor, HttpStatus.CREATED);
    }

    @PutMapping("/updateFloor/{id}")
    public ResponseEntity<Floor> updateFloor(@RequestBody Floor floor, @PathVariable Long id){
        Floor savedFloor = floorService.update(floor, id);
        return new ResponseEntity<>(savedFloor, HttpStatus.OK);
    }

    @DeleteMapping("deleteFloor/{id}")
    public ResponseEntity<String> deleteFloor(@PathVariable("id") Long id){
        floorService.delete(id);
        return ResponseEntity.ok("Floor deleted successfully!.");
    }
}
