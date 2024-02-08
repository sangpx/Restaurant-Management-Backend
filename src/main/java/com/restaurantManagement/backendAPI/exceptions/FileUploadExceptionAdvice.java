package com.restaurantManagement.backendAPI.exceptions;

import com.restaurantManagement.backendAPI.models.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//xử lý trường hợp yêu cầu vượt quá Kích thước tải lên tối đa.
// Hệ thống sẽ đưa ra MaxUploadSizeExceededException và sử dụng
// chú thích @ControllerAdvice và @ExceptionHandler để xử lý các trường hợp ngoại lệ

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<MessageResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new MessageResponse("File too large!"));
    }
}
