package com.restaurantManagement.backendAPI.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "Files")
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;


    /*
   chú thích @Lob .
   LOB là kiểu dữ liệu để lưu trữ dữ liệu đối tượng lớn.
   Có hai loại LOB: BLOB và CLOB:
        BLOB dùng để lưu trữ dữ liệu nhị phân
        CLOB dùng để lưu trữ dữ liệu văn bản
     */
    @Lob
    @Column(name = "data", columnDefinition="LONGBLOB")
    private byte[] data;

    public FileDB() {
    }

    public FileDB(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
