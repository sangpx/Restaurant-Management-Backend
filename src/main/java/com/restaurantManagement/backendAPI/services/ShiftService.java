package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Shift;

import java.util.List;

public interface ShiftService {
    Shift add(Shift shift, Long scheduleId);
    Shift update(Shift shift, Long id, Long scheduleId);
    void delete(Long id);
    Shift getDetail(Long id);
    List<Shift> getAll();
}
