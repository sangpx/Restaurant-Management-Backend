package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FileStorageService {
    //Tao thu muc Tĩnh
    private final Path root = Paths.get("uploads");

    //Ham tao thu muc luu - neu chua co thu muc
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    //Luu File
    @Override
    public String save(MultipartFile file) {
        try {
            //resolve tuong ung voi path folder
            Path destinationFile = this.root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(),
                    destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return destinationFile.toString(); // Trả về đường dẫn tới tệp đã lưu
        } catch (IOException ex) {
            if (ex instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            //resolve tuong ung voi path folder
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            //duyệt qua tất cả các tệp trong một thư mục
            /*
            - Files.walk() tạo ra một luồng (stream) của các đường dẫn
            (Path) đại diện cho tất cả các tệp và thư mục trong thư mục root.
            - filter(path -> !path.equals(this.root)): Lọc ra tất cả
            các đường dẫn không phải là thư mục gốc.
            Nó loại bỏ thư mục gốc khỏi kết quả cuối cùng để chỉ lấy các tệp con.
            - map(this.root::relativize): chuyển đổi mỗi đường dẫn
            thành đường dẫn tương đối đến this.root
             */
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
