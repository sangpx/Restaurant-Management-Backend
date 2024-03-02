package com.restaurantManagement.backendAPI.controllers.systemManage;


import com.restaurantManagement.backendAPI.models.entity.Sale;
import com.restaurantManagement.backendAPI.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping("/getAllSales")
    public ResponseEntity<?> getAllSales(){
        List<Sale> saleList = saleService.getAll();
        return ResponseEntity.ok(saleList);
    }

    @GetMapping("/getDetailSale/{id}")
    public ResponseEntity<Sale> getDetailSale(@PathVariable("id") Long id){
        Sale sale = saleService.getDetail(id);
        return ResponseEntity.ok(sale);
    }

    @PostMapping("/createSale")
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale){
        Sale savedSale = saleService.add(sale);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @PutMapping("/updateSale/{id}")
    public ResponseEntity<Sale> updateSale(@RequestBody Sale sale, @PathVariable Long id){
        Sale savedSale = saleService.update(sale, id);
        return new ResponseEntity<>(savedSale, HttpStatus.OK);
    }

    @DeleteMapping("deleteSale/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable("id") Long id){
        saleService.delete(id);
        return ResponseEntity.ok("Sale deleted successfully!.");
    }
}
