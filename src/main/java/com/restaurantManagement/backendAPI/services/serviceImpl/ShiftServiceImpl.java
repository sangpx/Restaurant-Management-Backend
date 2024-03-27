package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.Schedule;
import com.restaurantManagement.backendAPI.models.entity.Shift;
import com.restaurantManagement.backendAPI.repository.ShiftRepository;
import com.restaurantManagement.backendAPI.services.ShiftService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public Shift add(Shift shift, Long scheduleId) {
        Shift shiftCreate = new Shift();
        shiftCreate.setName(shift.getName());
        shiftCreate.setStartTime(shift.getStartTime());
        shiftCreate.setEndTime(shift.getEndTime());
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        shiftCreate.setSchedule(schedule);
        shiftCreate.setCreatedAt(Date.from(Instant.now()));
        shiftCreate.setUpdatedAt(Date.from(Instant.now()));
        return shiftRepository.save(shiftCreate);
    }

    @Override
    public Shift update(Shift shift, Long id, Long scheduleId) {
        Shift shiftExist = shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Shift with id: " + id));
        shiftExist.setName(shift.getName());
        shiftExist.setStartTime(shift.getStartTime());
        shiftExist.setEndTime(shift.getEndTime());
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        shiftExist.setSchedule(schedule);
        shiftExist.setUpdatedAt(Date.from(Instant.now()));
        return shiftRepository.save(shiftExist);
    }

    @Override
    public void delete(Long id) {
        Shift shiftExist = shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Shift with id: " + id));
        shiftRepository.delete(shiftExist);
    }

    @Override
    public Shift getDetail(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Shift with id: " + id));
    }

    @Override
    public List<Shift> getAll() {
        return shiftRepository.findAll();
    }
}
