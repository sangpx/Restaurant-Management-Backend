package com.restaurantManagement.backendAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Đánh dấu một Class Là tầng Repository, phục vụ truy xuất dữ liệu.
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
