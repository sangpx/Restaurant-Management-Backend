package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.Booking;
import com.restaurantManagement.backendAPI.models.entity.Category;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        //, EBookingStatus bookingStatus

        // Lấy deskId từ bookingDTO
        Long deskId = bookingDTO.getDeskId();
        // Lấy thông tin bàn từ CSDL
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn!"));
        // Kiểm tra trạng thái của bàn
        if (desk.getStatus() == EDeskStatus.BOOKED) {
            throw new NotFoundException("Mã bàn: " + desk.getId()
                    + " đã được đặt. Vui lòng tìm bàn khác!");
        }
        // Lấy thông tin về Nhân viên Đặt Bàn từ token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User currentUser = userRepository.findByUsername(currentUserName)
                .orElseThrow(()
                        -> new UserNotFoundException("Không tìm thấy thông tin người dùng!"));
        // Lưu thông tin đặt bàn cho bàn được chọn
        Booking bookingCreate = new Booking();
        bookingCreate.setDesk(desk);
        bookingCreate.setUser(currentUser);
        bookingCreate.setCustomerName(bookingDTO.getCustomerName());
        bookingCreate.setPhone(bookingDTO.getPhone());
        bookingCreate.setAddress(bookingDTO.getAddress());
        bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
        // Thiết lập trạng thái của đặt bàn
//            if (bookingStatus == EBookingStatus.PENDING) {
//                bookingCreate.setStatus(EBookingStatus.PENDING);
//            } else if (bookingStatus == EBookingStatus.CONFIRMED) {
//                bookingCreate.setStatus(EBookingStatus.CONFIRMED);
//            }
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
    public List<BookingDTO> addMultipleBookings(List<BookingDTO> bookingDTOs) {
        //, EBookingStatus bookingStatus
        List<BookingDTO> savedBookingDTOs = new ArrayList<>();
        // Lặp qua từng bookingDTOs
        for (BookingDTO bookingDTO : bookingDTOs) {
            // Lấy deskId từ bookingDTO
            Long deskId = bookingDTO.getDeskId();
            // Lấy thông tin bàn từ CSDL
            Desk desk = deskRepository.findById(deskId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn!"));
            // Kiểm tra trạng thái của bàn
            if (desk.getStatus() == EDeskStatus.BOOKED) {
                throw new NotFoundException("Mã bàn: " + desk.getId()
                        + " đã được đặt. Vui lòng tìm bàn khác!");
            }
            // Lấy thông tin về Nhân viên Đặt Bàn từ token JWT
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();

            // Lấy thông tin người dùng từ cơ sở dữ liệu
            User currentUser = userRepository.findByUsername(currentUserName)
                    .orElseThrow(()
                            -> new UserNotFoundException("Không tìm thấy thông tin người dùng!"));
            // Lưu thông tin đặt bàn cho bàn được chọn
            Booking bookingCreate = new Booking();
            bookingCreate.setDesk(desk);
            bookingCreate.setUser(currentUser);
            bookingCreate.setCustomerName(bookingDTO.getCustomerName());
            bookingCreate.setPhone(bookingDTO.getPhone());
            bookingCreate.setAddress(bookingDTO.getAddress());
            bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
            // Thiết lập trạng thái của đặt bàn
//            if (bookingStatus == EBookingStatus.PENDING) {
//                bookingCreate.setStatus(EBookingStatus.PENDING);
//            } else if (bookingStatus == EBookingStatus.CONFIRMED) {
//                bookingCreate.setStatus(EBookingStatus.CONFIRMED);
//            }
            bookingCreate.setCreatedAt(Date.from(Instant.now()));
            bookingCreate.setUpdatedAt(Date.from(Instant.now()));
            Booking savedBooking = bookingRepository.save(bookingCreate);

            // Cập nhật trạng thái của bàn
            desk.setStatus(EDeskStatus.BOOKED);
            desk.setUpdatedAt(new Date());
            deskRepository.save(desk);
            savedBookingDTOs.add(modelMapper.map(savedBooking, BookingDTO.class));
        }
        return savedBookingDTOs;
    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO, Long bookingId) {
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));
        // Lấy thông tin về Nhân viên Đặt Bàn từ token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User currentUser = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy thông tin người dùng!"));
        // Lưu thông tin đặt bàn cho bàn được chọn
        bookingExisted.setUser(currentUser);
//        bookingExisted.setReceivingTime(bookingDTO.getReceivingTime());
        bookingExisted.setCustomerName(bookingDTO.getCustomerName());
        bookingExisted.setPhone(bookingDTO.getPhone());
        bookingExisted.setAddress(bookingDTO.getAddress());
        bookingExisted.setQuantityPerson(bookingDTO.getQuantityPerson());
        // Kiểm tra và cập nhật trạng thái đặt bàn
//        if (bookingExisted.getStatus() == EBookingStatus.PENDING
//                && bookingDTO.getStatus() == EBookingStatus.CONFIRMED) {
//            bookingExisted.setStatus(EBookingStatus.CONFIRMED);
//        }
        bookingExisted.setUpdatedAt(Date.from(Instant.now()));
        Booking savedBooking = bookingRepository.save(bookingExisted);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }


    @Override
    public void delete(Long bookingId) {
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));
//        if(bookingExisted.getStatus() == EBookingStatus.PENDING) {
//            Desk desk = bookingExisted.getDesk();
//            desk.setStatus(EDeskStatus.EMPTY);
//            deskRepository.save(desk);
        bookingRepository.delete(bookingExisted);
//        } else {
//            throw new NotFoundException("Không thể xóa đặt bàn!");
//        }
    }

    @Override
    public BookingDTO getDetail(Long bookingId) {
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));
        return modelMapper.map(bookingExisted, BookingDTO.class);
    }


    @Override
    public List<BookingDTO> getAlls() {
        List<Booking> bookingList = bookingRepository.findAll();
        return bookingList
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }
}
