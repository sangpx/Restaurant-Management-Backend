package com.restaurantManagement.backendAPI.controllers;

import com.restaurantManagement.backendAPI.models.dto.payload.response.MessageResponse;
import com.restaurantManagement.backendAPI.models.entity.FileDB;
import com.restaurantManagement.backendAPI.services.serviceImpl.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/files")
public class FilesController {

    @Autowired
    private FilesStorageServiceImpl storageService;

    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);
            message = "Uploaded the file successfully!FileName: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message, true));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message, false));
        }
    }
    @GetMapping("/getListFiles")
    public ResponseEntity<List<FileDB>> getListFiles() {
        List<FileDB> fileDb = storageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class,
        "getFile", path.getFileName().toString()).build().toString();
            return new FileDB(fileName, url);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileDb);
    }

    @GetMapping("/getFile/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource file = storageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
