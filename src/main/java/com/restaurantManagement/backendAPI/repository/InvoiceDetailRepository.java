package com.restaurantManagement.backendAPI.repository;


import com.restaurantManagement.backendAPI.models.entity.InvoiceDetail;
import com.restaurantManagement.backendAPI.models.entity.keys.KeyInvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, KeyInvoiceDetail> {
    List<InvoiceDetail> findByKeyInvoiceDetail_InvoiceId(Long invoiceId);
}
