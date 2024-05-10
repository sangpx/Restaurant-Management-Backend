package com.restaurantManagement.backendAPI.services;

import com.restaurantManagement.backendAPI.models.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    //Tìm RefreshToken dựa trên id tức là chính mã thông báo
    Optional<RefreshToken> findByToken(String token);

    //Tạo và trả về Mã thông báo làm mới
    RefreshToken createRefreshToken(Long userId);

    // Kiểm tra xem token được cung cấp đã hết hạn hay chưa.
    // Nếu mã thông báo đã hết hạn, hãy xóa nó khỏi CSDL và ném TokenRefreshException
    RefreshToken verifyExpiration(RefreshToken token);
}
