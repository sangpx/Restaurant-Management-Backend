package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.BookingDTO;
import com.restaurantManagement.backendAPI.models.entity.*;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import com.restaurantManagement.backendAPI.models.entity.enums.EDeskStatus;
import com.restaurantManagement.backendAPI.repository.*;
import com.restaurantManagement.backendAPI.services.BookingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    //Khách hàng đặt bàn Online
    @Override
    public BookingDTO customerAddBooking(BookingDTO bookingDTO) {
        // Lấy thông tin về Nhân viên Đặt Bàn từ token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User currentUser = userRepository.findByUsername(currentUserName)
                .orElseThrow(()
                        -> new UserNotFoundException("Không tìm thấy thông tin người dùng!"));
        Booking bookingCreate = new Booking();
        bookingCreate.setCustomerName(bookingDTO.getCustomerName());
        bookingCreate.setPhone(bookingDTO.getPhone());
        bookingCreate.setUser(currentUser);
        bookingCreate.setAddress(bookingDTO.getAddress());
        bookingCreate.setEmail(bookingDTO.getEmail());

        // Chuyển đổi chuỗi bookingTime sang LocalDateTime và thiết lập vào đối tượng Booking
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime bookingTime = LocalDateTime.parse(bookingDTO.getBookingTime(), formatter);
        // Kiểm tra nếu ngày đặt nhỏ hơn ngày hiện tại
        if (bookingTime.isBefore(LocalDateTime.now())) {
            throw new NotFoundException("Ngày đặt phải lớn hơn ngày hiện tại!");
        }
        bookingCreate.setBookingTime(bookingTime);

        bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
        bookingCreate.setStatus(EBookingStatus.PENDING);
        bookingCreate.setCreatedAt(new Date());
        bookingCreate.setUpdatedAt(new Date());
        // Gửi email thông báo cho khách hàng
        sendBookingConfirmationEmail(bookingDTO);
        Booking savedBooking = bookingRepository.save(bookingCreate);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    //Nhân viên giữ bàn cho khách hàng
    @Override
    public BookingDTO holdingDeskCustomer(Long bookingId, Long deskId) {
        // Lấy thông tin bàn từ CSDL
        Desk deskExisted = deskRepository.findById(deskId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn ăn!"));
        // Kiểm tra trạng thái của bàn
        if (deskExisted.getStatus() != EDeskStatus.EMPTY) {
            throw new NotFoundException("Mã bàn: " + deskExisted.getId()
                    + " đã được đặt. Vui lòng tìm bàn khác!");
        }
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));

        bookingExisted.setDesk(deskExisted);
        deskExisted.setStatus(EDeskStatus.BOOKED);
        if (bookingExisted.getStatus() == EBookingStatus.PENDING) {
            bookingExisted.setStatus(EBookingStatus.HOLDING_A_SEAT);
        }
        deskExisted.setUpdatedAt(new Date());
        bookingExisted.setUpdatedAt(new Date());
        // Lưu các thay đổi vào cơ sở dữ liệu
        deskRepository.save(deskExisted);
        bookingRepository.save(bookingExisted);
        return modelMapper.map(bookingExisted, BookingDTO.class);
    }

    //Khi khách hàng đến nhà hàng, thì nhân viên sẽ ấn nút "Khách đã đến"
    @Override
    public BookingDTO confirmDeskCustomer(Long bookingId) {
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));
        if (bookingExisted.getStatus() == EBookingStatus.HOLDING_A_SEAT) {
            bookingExisted.setStatus(EBookingStatus.CONFIRMED);
        }
        bookingExisted.setUpdatedAt(new Date());
        bookingRepository.save(bookingExisted);
        return modelMapper.map(bookingExisted, BookingDTO.class);
    }


    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        // Lấy deskId từ bookingDTO
        Long deskId = bookingDTO.getDeskId();
        // Lấy thông tin bàn từ CSDL
        Desk desk = deskRepository.findById(deskId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn ăn!"));
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
        bookingCreate.setBookingTime(LocalDateTime.now());
        bookingCreate.setEmail(bookingDTO.getEmail());
        bookingCreate.setAddress(bookingDTO.getAddress());
        bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
        bookingCreate.setStatus(EBookingStatus.CONFIRMED);
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
        List<BookingDTO> savedBookingDTOs = new ArrayList<>();
        // Lặp qua từng bookingDTOs
        for (BookingDTO bookingDTO : bookingDTOs) {
            // Lấy deskId từ bookingDTO
            Long deskId = bookingDTO.getDeskId();
            // Lấy thông tin bàn từ CSDL
            Desk desk = deskRepository.findById(deskId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn ăn!"));
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
            bookingCreate.setEmail(bookingDTO.getEmail());
            bookingCreate.setQuantityPerson(bookingDTO.getQuantityPerson());
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
    public BookingDTO updateBooking(BookingDTO bookingDTO, Long bookingId) {
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
        bookingExisted.setCustomerName(bookingDTO.getCustomerName());
        bookingExisted.setPhone(bookingDTO.getPhone());
        bookingExisted.setAddress(bookingDTO.getAddress());
        bookingExisted.setEmail(bookingDTO.getEmail());
        bookingExisted.setQuantityPerson(bookingDTO.getQuantityPerson());
        bookingExisted.setStatus(EBookingStatus.CONFIRMED);
        bookingExisted.setUpdatedAt(Date.from(Instant.now()));
        Booking savedBooking = bookingRepository.save(bookingExisted);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }


    @Override
    public void delete(Long bookingId) {
        Booking bookingExisted = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin đặt bàn!"));
        bookingRepository.delete(bookingExisted);
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

    private void sendBookingConfirmationEmail(BookingDTO bookingDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        // Địa chỉ email của khách hàng
        message.setTo(bookingDTO.getEmail());
        message.setSubject("Xác nhận đặt bàn");
        message.setText("Xin chào " + bookingDTO.getCustomerName() + ",\n\n"
                + "Cảm ơn bạn đã đặt bàn tại nhà hàng chúng tôi. Dưới đây là thông tin chi tiết về đặt bàn của bạn:\n"
                + "Tên khách hàng: " + bookingDTO.getCustomerName() + "\n"
                + "Số điện thoại: " + bookingDTO.getPhone() + "\n"
                + "Địa chỉ: " + bookingDTO.getAddress() + "\n"
                + "Thời gian đặt bàn: " + bookingDTO.getBookingTime() + "\n"
                + "Số lượng khách: " + bookingDTO.getQuantityPerson() + "\n\n"
                + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.\n"
                + "Trân trọng,\n"
                + "Nhà hàng Grill63 - Khách sạn Lotte VN, 54 P. Liễu Giai, Ba Đình, Hà Nội");
        // Gửi email
        javaMailSender.send(message);
    }

    private void sendCancellationEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        // Địa chỉ email của khách hàng
        message.setTo(booking.getEmail());
        message.setSubject("Thông báo đặt bàn đã bị hủy");
        message.setText("Xin chào " + booking.getCustomerName() + ",\n\n"
                + "Chúng tôi rất tiếc phải thông báo rằng đặt bàn của bạn tại nhà hàng chúng tôi đã bị hủy.\n"
                + "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.\n"
                + "Hotline: +84 24 3333 1701\n\n"
                + "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.\n"
                + "Trân trọng,\n"
                + "Nhà hàng Grill63 - Khách sạn Lotte VN, 54 P. Liễu Giai, Ba Đình, Hà Nội");
        // Gửi email
        javaMailSender.send(message);
    }

    // Chạy mỗi 60 phút
//    @Scheduled(fixedRate = 3600000)
    @Scheduled(fixedRate = 60000) // Chạy mỗi 1 phút
    @Transactional // Đảm bảo giao dịch được mở để xóa các đặt bàn
    public void cleanupExpiredBookings() {
        try {
            // Lấy thời điểm hiện tại
            Date currentTime = new Date();
            // Lấy tất cả các đặt bàn với trạng thái "Holding_A_Seat"
            // và thời gian đặt bàn quá 60 phút
            List<Booking> expiredBookings = bookingRepository
                    .findByStatusAndCreatedAtBefore(EBookingStatus.HOLDING_A_SEAT,
                            new Date(currentTime.getTime() - 60 * 1000));
            // Xóa các đặt bàn quá hạn
            for (Booking booking : expiredBookings) {
                // Lấy deskId từ bookingDTO
                Long deskId = booking.getDesk().getId();
                // Lấy thông tin bàn từ CSDL
                Desk desk = deskRepository.findById(deskId)
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin bàn ăn!"));
                bookingRepository.delete(booking);
                // Gửi email thông báo hủy đặt bàn cho khách hàng
                sendCancellationEmail(booking);
                desk.setStatus(EDeskStatus.EMPTY);
                desk.setUpdatedAt(new Date());
                deskRepository.save(desk);
                logger.info("Hủy đặt bàn thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
