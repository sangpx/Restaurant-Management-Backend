package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer add(Customer customer);
    Customer update(Customer customer, Long id);
    void delete(Long id);
    Customer getDetail(Long id);
    List<Customer> getAll();
}
