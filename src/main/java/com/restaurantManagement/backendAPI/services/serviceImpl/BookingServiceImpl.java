package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.models.entity.Desk;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import com.restaurantManagement.backendAPI.models.entity.enums.EDeskStatus;
import com.restaurantManagement.backendAPI.repository.BookingRepository;
import com.restaurantManagement.backendAPI.repository.DeskRepository;
import com.restaurantManagement.backendAPI.repository.UserRepository;
import com.restaurantManagement.backendAPI.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeskRepository deskRepository;
    @Override
    public BookingDTO add(BookingDTO bookingDTO, Long deskId, Long userId) {
        // Lấy thông tin bàn từ cơ sở dữ liệu
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn!"));
        // Kiểm tra trạng thái của bàn
        if (desk.getStatus() == EDeskStatus.BOOKED) {
            throw new NotFoundException("Bàn này đã được đặt. Vui lòng tìm bàn khác!");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng!"));
        // Tạo và lưu thông tin đặt bàn
        Booking bookingCreate = new Booking();
        bookingCreate.setDesk(desk);
        bookingCreate.setTime(bookingDTO.getTime());
        bookingCreate.setUser(user);
        bookingCreate.setCustomerName(bookingDTO.getCustomerName());
        bookingCreate.setAddress(bookingDTO.getAddress());
        bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
        bookingCreate.setStatus(EBookingStatus.PENDING);
        bookingCreate.setCreatedAt(Date.from(Instant.now()));
        bookingCreate.setUpdatedAt(Date.from(Instant.now()));
        Booking savedBooking = bookingRepository.save(bookingCreate);
        // Cập nhật trạng thái của bàn
        desk.setStatus(EDeskStatus.BOOKED);
        desk.setUpdatedAt(new Date());
        deskRepository.save(desk);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public List<BookingDTO> addMultipleBookings(List<BookingDTO> bookingDTOs, Long userId) {
        List<BookingDTO> savedBookingDTOs = new ArrayList<>();
        
        return null;
    }

    @Override
    public Booking update(Booking booking, Long bookingId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Booking getDetail(Long id) {
        return null;
    }

    @Override
    public List<Booking> searchBooking(String query) {
        return null;
    }

    @Override
    public List<Booking> getAlls() {
        return null;
    }
}
