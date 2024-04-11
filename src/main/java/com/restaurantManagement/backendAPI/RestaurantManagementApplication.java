package com.restaurantManagement.backendAPI;

import com.restaurantManagement.backendAPI.services.FileStorageService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class RestaurantManagementApplication implements CommandLineRunner {
	//Cau hinh modelMapper trong project
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {

		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
//    	fileStorageService.deleteAll();
		fileStorageService.init();
	}
}
