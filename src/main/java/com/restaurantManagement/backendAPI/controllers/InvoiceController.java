package com.restaurantManagement.backendAPI.controllers;


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

public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getAlls")
    public ResponseEntity<List<InvoiceDTO>> getAlls() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

//    @PreAuthorize("hasRole(('CASHIER'))")
//    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getInvoiceByBookingId/{bookingId}")
    public ResponseEntity<InvoiceDTO> getInvoiceByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(invoiceService.getInvoiceByBookingId(bookingId));
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getInvoiceById/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(invoiceId));
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@RequestParam Long bookingId) {
        return new ResponseEntity<>(invoiceService.createInvoice(bookingId),
                HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PutMapping("/update")
    public ResponseEntity<InvoiceDetailDTO> updateInvoice(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        return ResponseEntity.ok(invoiceService.updateInvoiceDetail(invoiceDetailDTO));
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PostMapping("/addFoodToInvoice")
    public ResponseEntity<InvoiceDetailDTO> addFoodToInvoice(@RequestBody InvoiceDetailDTO invoiceDetailDTO) {
        return ResponseEntity.ok(invoiceService.addFoodToInvoiceDetail(invoiceDetailDTO));
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @PostMapping("/{invoiceId}/pay")
    public ResponseEntity<String> payInvoice(@PathVariable Long invoiceId) {
        invoiceService.payInvoice(invoiceId);
        return ResponseEntity.ok("Hóa đơn đã được thanh toán thành công!");
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteInvoiceDetail(@RequestParam Long invoiceId, @RequestParam Long foodId) {
        invoiceService.removeFoodFromInvoiceDetail(invoiceId, foodId);
        return ResponseEntity.ok("Món ăn đã được xóa thành công!");
    }
//    @PreAuthorize("hasRole(('CASHIER'))")
    @PreAuthorize("hasRole(('ADMIN'))")
    @GetMapping("/getInvoiceDetail/{invoiceId}")
    public ResponseEntity<List<InvoiceDetailDTO>> getDetailInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceDetailsByInvoiceId(invoiceId));
    }
}
