package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.models.entity.FileDB;
import com.restaurantManagement.backendAPI.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl  {

    @Autowired
    private FileDBRepository fileDBRepository;


    //storeFile(file) nhận đối tượng MultipartFile , chuyển đổi thành đối tượng FileDB và lưu vào CSDL
    //Luu tru File
    public FileDB storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fileDB);
    }

    //getFile(id): trả về một đối tượng FileDB theo Id được cung cấp
    //Get File
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }


    //getAllFiles(): trả về tất cả các tệp được lưu trữ dưới dạng danh sách mã đối tượng FileDB
    //Get All File
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
