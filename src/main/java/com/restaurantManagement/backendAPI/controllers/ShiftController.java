package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.entity.Shift;
import com.restaurantManagement.backendAPI.services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class ShiftController {
    @Autowired
    private ShiftService shiftService;

    @GetMapping("/getAlls")
    public ResponseEntity<List<Shift>> getAlls(){
        List<Shift> shifts = shiftService.getAll();
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/getDetailShift/{id}")
    public ResponseEntity<Shift> getDetailShift(@PathVariable("id") Long id){
        Shift shift = shiftService.getDetail(id);
        return ResponseEntity.ok(shift);
    }
    @PostMapping("/createShift")
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift, @RequestParam Long scheduleId){
        return new ResponseEntity<>(shiftService.add(shift, scheduleId), HttpStatus.CREATED);
    }

    @PutMapping("/updateShift/{id}")
    public ResponseEntity<Shift> updateShift(@RequestBody Shift shift, @PathVariable Long id,
                                                @RequestParam Long scheduleId){
        return new ResponseEntity<>(shiftService.update(shift, id, scheduleId), HttpStatus.OK);
    }

    @DeleteMapping("deleteShift/{id}")
    public ResponseEntity<String> deleteShift(@PathVariable("id") Long id){
        shiftService.delete(id);
        return ResponseEntity.ok("Xóa ca làm việc thành công!");
    }
}
