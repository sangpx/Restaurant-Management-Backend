package com.restaurantManagement.backendAPI.models.dto.payload.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private String name;
    private String url;
    private String type;
    private long size;
}
