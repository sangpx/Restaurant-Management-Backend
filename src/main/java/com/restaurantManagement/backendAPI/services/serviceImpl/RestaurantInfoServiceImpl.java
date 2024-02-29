package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Floor;
import com.restaurantManagement.backendAPI.models.entity.RestaurantInfo;
import com.restaurantManagement.backendAPI.repository.RestaurantInfoRepository;
import com.restaurantManagement.backendAPI.services.RestaurantInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class RestaurantInfoServiceImpl implements RestaurantInfoService {

    @Autowired
    private RestaurantInfoRepository restaurantInfoRepository;
    @Override
    public RestaurantInfo add(RestaurantInfo restaurantInfo) {
        RestaurantInfo createInfo = new RestaurantInfo();
        createInfo.setName(restaurantInfo.getName());
        createInfo.setEmail(restaurantInfo.getEmail());
        createInfo.setPhone(restaurantInfo.getPhone());
        createInfo.setAddress(restaurantInfo.getAddress());
        createInfo.setAbout(restaurantInfo.getAbout());
        createInfo.setTimeOpen(restaurantInfo.getTimeOpen());
        createInfo.setTimeClose(restaurantInfo.getTimeClose());
        createInfo.setCreatedAt(Date.from(Instant.now()));
        createInfo.setUpdatedAt(Date.from(Instant.now()));
        return restaurantInfoRepository.save(createInfo);
    }

    @Override
    public RestaurantInfo update(RestaurantInfo restaurantInfo, Long id) {
        RestaurantInfo resExist = restaurantInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found with: " + id));
        resExist.setName(restaurantInfo.getName());
        resExist.setEmail(restaurantInfo.getEmail());
        resExist.setPhone(restaurantInfo.getPhone());
        resExist.setAddress(restaurantInfo.getAddress());
        resExist.setAbout(restaurantInfo.getAbout());
        resExist.setTimeOpen(restaurantInfo.getTimeOpen());
        resExist.setTimeClose(restaurantInfo.getTimeClose());
        resExist.setUpdatedAt(Date.from(Instant.now()));
        return restaurantInfoRepository.save(resExist);
    }
}
