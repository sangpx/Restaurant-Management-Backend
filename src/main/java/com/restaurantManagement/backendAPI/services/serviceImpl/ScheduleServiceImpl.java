package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.Schedule;
import com.restaurantManagement.backendAPI.repository.ScheduleRepository;
import com.restaurantManagement.backendAPI.services.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule add(Schedule schedule) {
        Schedule scheduleCreate = new Schedule();
        scheduleCreate.setName(schedule.getName());
        scheduleCreate.setWorkingTime(schedule.getWorkingTime());
        scheduleCreate.setCreatedAt(Date.from(Instant.now()));
        scheduleCreate.setUpdatedAt(Date.from(Instant.now()));
        return scheduleRepository.save(scheduleCreate);
    }

    @Override
    public Schedule update(Schedule schedule, Long id) {
        Schedule scheduleExist = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin lịch!"));
        scheduleExist.setName(schedule.getName());
        scheduleExist.setWorkingTime(schedule.getWorkingTime());
        scheduleExist.setUpdatedAt(Date.from(Instant.now()));
        return scheduleRepository.save(scheduleExist);
    }

    @Override
    public void delete(Long id) {
        Schedule scheduleExist = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin lịch!"));
        scheduleRepository.delete(scheduleExist);
    }

    @Override
    public Schedule getDetail(Long id) {
        Schedule scheduleExist = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin lịch!"));
        return scheduleExist;
    }

//    @Override
//    public List<Schedule> searchSchedule(String query) {
//        return null;
//    }

    @Override
    public List<Schedule> getAlls() {
        return scheduleRepository.findAll();
    }
}
