package com.restaurantManagement.backendAPI.controllers;


import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDetailDTO;
import com.restaurantManagement.backendAPI.models.entity.Invoice;
import com.restaurantManagement.backendAPI.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/getAlls")
    public ResponseEntity<List<InvoiceDTO>> getAlls() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestParam Long bookingId) {
        return new ResponseEntity<>(invoiceService.createInvoice(bookingId),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateInvoice(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        invoiceService.updateInvoiceDetail(invoiceDetailDTO);
        return ResponseEntity.ok("Món ăn đã được cập nhật thành công!");
    }

    @PostMapping("/addFoodToInvoice")
    public ResponseEntity<String> addFoodToInvoice(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        invoiceService.addFoodToInvoice(invoiceDetailDTO);
        return ResponseEntity.ok("Món ăn đã được thêm vào hóa đơn thành công!");
    }

    @PostMapping("/{invoiceId}/pay")
    public ResponseEntity<String> payInvoice(@PathVariable Long invoiceId) {
        invoiceService.payInvoice(invoiceId);
        return ResponseEntity.ok("Hóa đơn đã được thanh toán thành công!");
    }
}
