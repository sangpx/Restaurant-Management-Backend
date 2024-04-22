package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDetailDTO;
import com.restaurantManagement.backendAPI.models.entity.Invoice;
import com.restaurantManagement.backendAPI.models.entity.InvoiceDetail;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getAll();

    Invoice createInvoice(Long bookingId);

    InvoiceDetailDTO addFoodToInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO);

    InvoiceDetailDTO updateInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO);

    void removeFoodFromInvoiceDetail(Long invoiceId, Long foodId);


    void payInvoice(Long invoiceId);

    List<InvoiceDetailDTO> getInvoiceDetailsByInvoiceId(Long invoiceId);

    InvoiceDTO getInvoiceById(Long invoiceId);


    InvoiceDTO getInvoiceByBookingId(Long bookingId);


}
