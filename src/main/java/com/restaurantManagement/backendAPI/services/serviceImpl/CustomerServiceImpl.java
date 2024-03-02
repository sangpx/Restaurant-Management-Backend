package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.Customer;
import com.restaurantManagement.backendAPI.repository.CustomerRepository;
import com.restaurantManagement.backendAPI.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer add(Customer customer) {
        Customer createCustomer = new Customer();
        createCustomer.setName(customer.getName());
        createCustomer.setEmail(customer.getEmail());
        createCustomer.setPhone(customer.getPhone());
        createCustomer.setQuantityPerson(customer.getQuantityPerson());
        createCustomer.setNote(customer.getNote());
        createCustomer.setStatus(true);
        createCustomer.setAccumulatedPoints(10);
        createCustomer.setTimeArrived(customer.getTimeArrived());
        createCustomer.setCreatedAt(Date.from(Instant.now()));
        createCustomer.setUpdatedAt(Date.from(Instant.now()));
        return customerRepository.save(createCustomer);
    }

    @Override
    public Customer update(Customer customer, Long id) {
        Customer customerExist = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Customer with: " + id));
        customerExist.setName(customer.getName());
        customerExist.setEmail(customer.getEmail());
        customerExist.setPhone(customer.getPhone());
        customerExist.setQuantityPerson(customer.getQuantityPerson());
        customerExist.setNote(customer.getNote());
        customerExist.setStatus(!customerExist.isStatus());
        customerExist.setAccumulatedPoints(customer.getAccumulatedPoints());
        customerExist.setTimeArrived(customer.getTimeArrived());
        customerExist.setUpdatedAt(Date.from(Instant.now()));
        return customerRepository.save(customerExist);
    }

    @Override
    public void delete(Long id) {
        Customer customerExist = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Customer with: " + id));
        customerRepository.delete(customerExist);
    }

    @Override
    public Customer getDetail(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Customer with: " + id));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
