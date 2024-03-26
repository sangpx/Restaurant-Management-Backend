package com.restaurantManagement.backendAPI.controllers;


import com.restaurantManagement.backendAPI.models.dto.payload.response.PageResult;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.services.DeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class DeskController {
    @Autowired
    private DeskService deskService;

    @GetMapping("/getDesksPaging")
    public PageResult<Page<Desk>> getDesksPaging(
            @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam String filed){
        Page<Desk> deskPages = deskService.getDesksWithPaginationAndSorting(pageNumber, pageSize, filed);
        return new PageResult<>(deskPages.getSize(), deskPages);
    }

    @GetMapping("/getAlls")
    public ResponseEntity<List<Desk>> getAlls(){
        List<Desk> deskDTOList = deskService.getAlls();
        return ResponseEntity.ok(deskDTOList);
    }

    @GetMapping("/getDetailDesk/{id}")
    public ResponseEntity<Desk> getDetailDesk(@PathVariable("id") Long id){
        Desk desk = deskService.getDetail(id);
        return ResponseEntity.ok(desk);
    }

    @PostMapping("/createDesk")
    public ResponseEntity<Desk> createDesk(@RequestBody Desk desk){
        Desk savedDesk = deskService.add(desk);
        return new ResponseEntity<>(savedDesk, HttpStatus.CREATED);
    }

    @PutMapping("/updateDesk")
    public ResponseEntity<Desk> updateDesk(@RequestBody Desk desk, @RequestParam Long floorId, @RequestParam Long deskId){
        Desk savedDesk = deskService.update(desk, deskId);
        return new ResponseEntity<>(savedDesk, HttpStatus.OK);
    }

    @DeleteMapping("deleteDesk/{id}")
    public ResponseEntity<String> deleteDesk(@PathVariable("id") Long id){
        deskService.delete(id);
        return ResponseEntity.ok("Desk deleted successfully!.");
    }

    @GetMapping("/searchDesks")
    public ResponseEntity<List<Desk>> searchDesks(
            @RequestParam("query") String query){
        return ResponseEntity.ok(deskService.searchDesk(query));
    }
}
