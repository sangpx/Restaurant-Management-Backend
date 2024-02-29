package com.restaurantManagement.backendAPI.controllers.systemManage;

import com.restaurantManagement.backendAPI.models.entity.RestaurantInfo;
import com.restaurantManagement.backendAPI.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant-infoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class RestaurantInfoController {
    @Autowired
    private RestaurantInfoService restaurantInfoService;

    @PostMapping("/createRestaurantInfo")
    public ResponseEntity<RestaurantInfo> createFloor(@RequestBody RestaurantInfo restaurantInfo){
        RestaurantInfo savedRestaurantInfo = restaurantInfoService.add(restaurantInfo);
        return new ResponseEntity<>(savedRestaurantInfo, HttpStatus.CREATED);
    }
    @PutMapping("/updateRestaurantInfo/{id}")
    public ResponseEntity<RestaurantInfo> updateRestaurantInfo(@RequestBody RestaurantInfo restaurantInfo, @PathVariable Long id){
        RestaurantInfo savedRestaurantInfo = restaurantInfoService.update(restaurantInfo, id);
        return new ResponseEntity<>(savedRestaurantInfo, HttpStatus.OK);
    }
}
