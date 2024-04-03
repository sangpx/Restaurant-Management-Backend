package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDetailDTO;
import com.restaurantManagement.backendAPI.models.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAll();
    Invoice createInvoice(Long bookingId);
    void addFoodToInvoice(InvoiceDetailDTO invoiceDetailDTO);

    void updateInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO);
//
//            void removeInvoiceDetail()

    void payInvoice(Long invoiceId);
}
