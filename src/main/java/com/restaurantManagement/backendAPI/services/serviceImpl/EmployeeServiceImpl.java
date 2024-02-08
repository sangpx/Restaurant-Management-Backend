package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.repository.EmployeeRepository;
import com.restaurantManagement.backendAPI.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Đánh dấu một Class là tầng Service, phục vụ các logic nghiệp vụ.

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return  employeeRepository.findAll();
    }

    //if there are not any department with that id than this service return object with null values
    @Override
    public Employee getEmployeeById(Integer id) {
        return  employeeRepository.findById(id).orElse(new Employee());
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return  employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        //Tim Kiem Department da ton tai
        Employee existingEmployee = employeeRepository.findById(employee.getEmployeeID()).orElse(null);
        //Thuc hien Update Employee
        existingEmployee.setEmployeeCode(employee.getEmployeeCode());
        existingEmployee.setEmployeeName(employee.getEmployeeName());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setAccountBank(employee.getAccountBank());
        existingEmployee.setBranchBank(employee.getBranchBank());
        existingEmployee.setNameBank(employee.getNameBank());
        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setCareerTitle(employee.getCareerTitle());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setIdentityIssureDate(employee.getIdentityIssureDate());
        existingEmployee.setIdentityNumber(employee.getIdentityNumber());
        existingEmployee.setLandlineNumber(employee.getLandlineNumber());
        existingEmployee.setIdentityNumber(employee.getIdentityNumber());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
        existingEmployee.setGender(employee.getGender());
        return  employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }


    /*
        - Phương thức findAll (Pageable pageable) theo mặc định trả về một đối tượng Page<T>
        - PageRequest là một lớp trong JPA dùng để đại diện cho yêu cầu phân trang.
        Nó thường được sử dụng khi bạn muốn lấy một trang cụ thể của dữ liệu từ CSDL.
        + offSet: Đây là vị trí bắt đầu của trang. Vị trí này được tính từ 0, nghĩa là offSet = 0
        + pageSize: Đây là kích thước của mỗi trang, tức là số lượng phần tử bạn muốn lấy từ cơ sở dữ liệu cho mỗi trang
     */
    @Override
    public Page<Employee> getAllPagingEmployees(int offSet, int pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(offSet, pageSize));
        return  employees;
    }
}
