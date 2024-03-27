package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.entity.Schedule;
import com.restaurantManagement.backendAPI.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/getAlls")
    public ResponseEntity<List<Schedule>> getAlls(){
        List<Schedule> schedules = scheduleService.getAlls();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/getDetailSchedule/{id}")
    public ResponseEntity<Schedule> getDetailSchedule(@PathVariable("id") Long id){
        Schedule schedule = scheduleService.getDetail(id);
        return ResponseEntity.ok(schedule);
    }
    @PostMapping("/createSchedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule){
        return new ResponseEntity<>(scheduleService.add(schedule), HttpStatus.CREATED);
    }

    @PutMapping("/updateSchedule/{id}")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule, @PathVariable Long id){
        return new ResponseEntity<>(scheduleService.update(schedule, id), HttpStatus.OK);
    }

    @DeleteMapping("deleteSchedule/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable("id") Long id){
        scheduleService.delete(id);
        return ResponseEntity.ok("Schedule deleted successfully!.");
    }
//
//    @GetMapping("/searchSchedules")
//    public ResponseEntity<List<Schedule>> searchSchedules(
//            @RequestParam("query") String query){
//        return ResponseEntity.ok(scheduleService.searchSchedule(query));
//    }
}
