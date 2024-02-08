package com.restaurantManagement.backendAPI.repository;

import com.restaurantManagement.backendAPI.models.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//save(FileDB), findById(id), findAll()
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
