package com.restaurantManagement.backendAPI.controllers.systemManage;

import com.restaurantManagement.backendAPI.models.entity.Customer;
import com.restaurantManagement.backendAPI.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(){
        List<Customer> customerList = customerService.getAll();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/getDetailCustomer/{id}")
    public ResponseEntity<Customer> getDetailCustomer(@PathVariable("id") Long id){
        Customer customer = customerService.getDetail(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.add(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id){
        Customer savedCustomer = customerService.update(customer, id);
        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("deleteCustomer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id){
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted successfully!.");
    }
}
