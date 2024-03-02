package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Sale;
import com.restaurantManagement.backendAPI.repository.SaleRepository;
import com.restaurantManagement.backendAPI.services.SaleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale add(Sale sale) {
        Sale createSale = new Sale();
        createSale.setName(sale.getName());
        createSale.setCode(sale.getCode());
        createSale.setDiscount(sale.getDiscount());
        createSale.setTimeStart(sale.getTimeStart());
        createSale.setTimeEnd(sale.getTimeEnd());
        createSale.setCreatedAt(Date.from(Instant.now()));
        createSale.setUpdatedAt(Date.from(Instant.now()));
        return saleRepository.save(createSale);
    }

    @Override
    public Sale update(Sale sale, Long id) {
        Sale saleExist = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Sale with: " + id));
        saleExist.setName(sale.getName());
        saleExist.setCode(sale.getCode());
        saleExist.setDiscount(sale.getDiscount());
        saleExist.setTimeStart(sale.getTimeStart());
        saleExist.setTimeEnd(sale.getTimeEnd());
        saleExist.setUpdatedAt(Date.from(Instant.now()));
        return saleRepository.save(saleExist);
    }

    @Override
    public void delete(Long id) {
        Sale saleExist = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Sale with: " + id));
        saleRepository.delete(saleExist);
    }

    @Override
    public Sale getDetail(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Sale with: " + id));
    }

    @Override
    public List<Sale> getAll() {
        return saleRepository.findAll();
    }
}
