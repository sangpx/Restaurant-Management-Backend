package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule add(Schedule schedule);
    Schedule update(Schedule schedule, Long id);
    void delete(Long id);
    Schedule getDetail(Long id);
//    List<Schedule> searchSchedule(String query);
    List<Schedule> getAlls();
}
