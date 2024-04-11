package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.NotFoundException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDTO;
import com.restaurantManagement.backendAPI.models.dto.catalog.InvoiceDetailDTO;
import com.restaurantManagement.backendAPI.models.entity.*;
import com.restaurantManagement.backendAPI.models.entity.enums.EBookingStatus;
import com.restaurantManagement.backendAPI.models.entity.enums.EDeskStatus;
import com.restaurantManagement.backendAPI.models.entity.enums.EInvoiceStatus;
import com.restaurantManagement.backendAPI.models.entity.keys.KeyInvoiceDetail;
import com.restaurantManagement.backendAPI.repository.*;
import com.restaurantManagement.backendAPI.services.InvoiceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<InvoiceDTO> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            // Sao chép các thuộc tính từ Invoice sang InvoiceDTO
            invoiceDTO.setId(invoice.getId());
            invoiceDTO.setDate(invoice.getDate());
            invoiceDTO.setTotalPrice(invoice.getTotalPrice());
            invoiceDTO.setBookingId(invoice.getBooking().getId());
            invoiceDTO.setStatus(invoice.getStatus());
            invoiceDTO.setCheckInTime(invoice.getCheckInTime());
            invoiceDTO.setCheckOutTime(invoice.getCheckOutTime());
            invoiceDTO.setCreatedAt(invoice.getCreatedAt());
            invoiceDTO.setUpdatedAt(invoice.getUpdatedAt());
            // Đặt giá trị deskId
            if (invoice.getBooking() != null) {
                invoiceDTO.setDeskId(invoice.getBooking().getDesk().getId());
            }
            invoiceDTOList.add(invoiceDTO);
        }
        return invoiceDTOList;
    }

    @Override
    public Invoice createInvoice(Long bookingId) {
        // Kiểm tra xem đặt bàn có hợp lệ không
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đặt bàn hợp lệ!"));
        // Kiểm tra trạng thái của hoá đơn
        if (booking.getInvoiceList()
                .stream()
                .anyMatch(invoice -> invoice.getStatus() != EInvoiceStatus.PAID)) {
            throw new NotFoundException("Đặt bàn này chưa được thanh toán!");
        }

        if (booking.getStatus() == EBookingStatus.PENDING
                || booking.getStatus() == EBookingStatus.HOLDING_A_SEAT ) {
            throw new NotFoundException("Không thể tạo hóa đơn khi đặt bàn ở trạng thái này!");
        }

        if (booking.getStatus() == EBookingStatus.INACTIVE) {
            throw new NotFoundException("Không thể tạo hóa đơn từ đặt bàn đã hoàn thành!");
        }

        booking.setStatus(EBookingStatus.WORKING);
        bookingRepository.save(booking);

        // Lấy thông tin về Nhân viên Đặt Bàn từ token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User currentUser = userRepository.findByUsername(currentUserName)
                .orElseThrow(()
                        -> new UserNotFoundException("Không tìm thấy thông tin người dùng!"));

        // Tạo một hóa đơn mới và gắn nó với đặt bàn
        Invoice invoiceCreate = new Invoice();
        invoiceCreate.setBooking(booking);
        invoiceCreate.setUser(currentUser);
        invoiceCreate.setStatus(EInvoiceStatus.PENDING);
        invoiceCreate.setCheckInTime(new Date());
        invoiceCreate.setDate(new Date());
        invoiceCreate.setCreatedAt(new Date());
        invoiceCreate.setUpdatedAt(new Date());
        invoiceRepository.save(invoiceCreate);
        return invoiceCreate;
    }

    @Override
    public InvoiceDetailDTO addFoodToInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO) {
        Invoice invoice = invoiceRepository.findById(invoiceDetailDTO.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin hóa đơn!"));

        // Kiểm tra xem hóa đơn có ở trạng thái "PAID" không
        if (invoice.getStatus() == EInvoiceStatus.PAID) {
            throw new NotFoundException("Hóa đơn đã được thanh toán, không thể thêm món ăn!");
        }

        Food food = foodRepository.findById(invoiceDetailDTO.getFoodId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin món ăn!"));

        // Tạo đối tượng KeyInvoiceDetail
        KeyInvoiceDetail key = new KeyInvoiceDetail();
        key.setInvoiceId(invoiceDetailDTO.getInvoiceId());
        key.setFoodId(invoiceDetailDTO.getFoodId());

        //Tạo CT Hóa Đơn
        InvoiceDetail newInvoiceDetail = new InvoiceDetail();
        newInvoiceDetail.setKeyInvoiceDetail(key);
        newInvoiceDetail.setInvoice(invoice);
        newInvoiceDetail.setFood(food);
        newInvoiceDetail.setQuantity(invoiceDetailDTO.getQuantity());
        newInvoiceDetail.setIntoMoney(food.getPrice() * invoiceDetailDTO.getQuantity());
        newInvoiceDetail.setCreatedAt(new Date());
        newInvoiceDetail.setUpdatedAt(new Date());
        InvoiceDetail invoiceDetailSaved = invoiceDetailRepository.save(newInvoiceDetail);

        // Cập nhật trạng thái của hóa đơn thành "ORDERED_FOOD"
        invoice.setStatus(EInvoiceStatus.ORDERED_FOOD);
        // Cập nhật tổng tiền của hóa đơn
        updateTotalPrice(invoice);
        invoiceRepository.save(invoice);
        return modelMapper.map(invoiceDetailSaved, InvoiceDetailDTO.class);
    }

    @Override
    public InvoiceDetailDTO updateInvoiceDetail(InvoiceDetailDTO invoiceDetailDTO) {
        Invoice invoice = invoiceRepository.findById(invoiceDetailDTO.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin hóa đơn!"));
        // Kiểm tra xem hóa đơn có ở trạng thái "PAID" không
        if (invoice.getStatus() == EInvoiceStatus.PAID) {
            throw new NotFoundException("Không thể chỉnh sửa hóa đơn đã thanh toán!");
        }
        // Kiểm tra chi tiết hóa đơn tồn tại
        KeyInvoiceDetail key = new KeyInvoiceDetail();
        key.setInvoiceId(invoiceDetailDTO.getInvoiceId());
        key.setFoodId(invoiceDetailDTO.getFoodId());
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(key)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn chi tiết"));
        invoiceDetail.setQuantity(invoiceDetailDTO.getQuantity());

        // Tính thành tiền của món
        double intoMoney = invoiceDetail.getQuantity() * invoiceDetail.getFood().getPrice();
        invoiceDetail.setIntoMoney(intoMoney);

        invoiceDetail.setUpdatedAt(new Date());
        // Lưu chi tiết hóa đơn
        InvoiceDetail invoiceDetailSaved = invoiceDetailRepository.save(invoiceDetail);

        // Cập nhật tổng tiền của hóa đơn
        updateTotalPrice(invoice);
        invoice.setUpdatedAt(new Date());
        invoiceRepository.save(invoice);
        return modelMapper.map(invoiceDetailSaved, InvoiceDetailDTO.class);
    }

    @Override
    public void removeFoodFromInvoiceDetail(Long invoiceId, Long foodId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin hóa đơn!"));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin món ăn!"));

        // Tìm kiếm chi tiết hóa đơn dựa trên invoiceId và foodId
        KeyInvoiceDetail key = new KeyInvoiceDetail(invoiceId, foodId);
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(key)
                .orElseThrow(() ->
                        new NotFoundException("Không tìm thấy món ăn trong chi tiết hóa đơn!"));

        // Xóa món ăn khỏi chi tiết hóa đơn
        invoiceDetailRepository.delete(invoiceDetail);

        // Kiểm tra số lượng mục chi tiết hóa đơn sau khi xóa
        List<InvoiceDetail> remainingDetails = invoiceDetailRepository
                .findByKeyInvoiceDetail_InvoiceId(invoiceId);
        if (remainingDetails.isEmpty()) {
            invoice.setStatus(EInvoiceStatus.PENDING);
        }

        // Cập nhật tổng tiền của hóa đơn
        updateTotalPrice(invoice);
        invoice.setUpdatedAt(new Date());
        invoiceRepository.save(invoice);
    }

    @Override
    public void payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thông tin hóa đơn!"));

        if (invoice.getStatus() == EInvoiceStatus.PENDING) {
            throw new NotFoundException("Không thể thanh toán hóa đơn khi ở trong trạng thái Đang chờ!");
        }

        // Cập nhật trạng thái của hóa đơn thành "PAID" và giờ ra
        invoice.setStatus(EInvoiceStatus.PAID);
        invoice.setCheckOutTime(new Date());
        invoiceRepository.save(invoice);

        // Cập nhật trạng thái của bàn thành "EMPTY"
        invoice.getBooking().getDesk().setStatus(EDeskStatus.EMPTY);
        deskRepository.save(invoice.getBooking().getDesk());

        // Cập nhật trạng thái của đặt bàn thành "INACTIVE"
        invoice.getBooking().setStatus(EBookingStatus.INACTIVE);
        bookingRepository.save(invoice.getBooking());
    }

    @Override
    public List<InvoiceDetailDTO> getInvoiceDetailsByInvoiceId(Long invoiceId) {
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByKeyInvoiceDetail_InvoiceId(invoiceId);;
        List<InvoiceDetailDTO> invoiceDetailDTOS = new ArrayList<>();
        for (InvoiceDetail invoiceDetail : invoiceDetails) {
            InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
            // Sao chép các thuộc tính từ Invoice sang InvoiceDTO
            invoiceDetailDTO.setInvoiceId(invoiceDetail.getKeyInvoiceDetail().getInvoiceId());
            invoiceDetailDTO.setFoodId(invoiceDetail.getKeyInvoiceDetail().getFoodId());
            invoiceDetailDTO.setQuantity(invoiceDetail.getQuantity());
            invoiceDetailDTO.setIntoMoney(invoiceDetail.getIntoMoney());
            invoiceDetailDTO.setNameFood(invoiceDetail.getFood().getName());
            invoiceDetailDTOS.add(invoiceDetailDTO);
        }
        return invoiceDetailDTOS;
    }


    // Phương thức để cập nhật tổng tiền của hóa đơn
    private void updateTotalPrice(Invoice invoice) {
        double totalPrice = 0;
        for (InvoiceDetail detail : invoice.getInvoiceDetailList()) {
            totalPrice += detail.getIntoMoney();
        }
        invoice.setTotalPrice(totalPrice);
        invoiceRepository.save(invoice);
    }
}
