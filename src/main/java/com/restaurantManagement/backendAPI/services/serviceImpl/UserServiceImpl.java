package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.UserAlreadyExistException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.UserDTO;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.repository.UserRepository;
import com.restaurantManagement.backendAPI.security.jwt.JwtUtils;
import com.restaurantManagement.backendAPI.security.services.UserDetailsImpl;
import com.restaurantManagement.backendAPI.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean existsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public User add(User user) {
        Optional<User> theUser = userRepository.findByUsername(user.getUsername());
        if (theUser.isPresent()){
            throw new UserAlreadyExistException("Người dùng" + user.getUsername() + " đã tồn tại!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhone(user.getPhone());
        user.setEmail(user.getEmail());
        user.setStatus(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public User update(User user, Long id) {
        User userFind = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng!"));

        userFind.setUsername(user.getUsername());
        userFind.setPhone(user.getPhone());
        userFind.setEmail(user.getEmail());
        // Lấy giá trị hiện tại của trường status
        boolean currentStatus = userFind.isStatus();
        // Đảo ngược giá trị của status
        userFind.setStatus(!currentStatus);
//        userFind.setRoles(user.getRoles());
        userFind.setUpdatedAt(new Date());
        return userRepository.save(userFind);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng!"));
        return modelMapper.map(user, UserDTO.class);
    }

    /*
    PageRequest để chỉ định số trang (pageNumber), kích thước trang
     (pageSize) và thứ tự sắp xếp (field).
     */
    @Override
    public Page<UserDTO> getUsersWithPaginationAndSorting(int pageNumber,
         int pageSize, String filed) {
        //DTO -> Entity
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize)
                .withSort(Sort.by(filed)));
        //Entity -> DTO
        Page<UserDTO> userDTOPage = userPage.map(user -> modelMapper.map(user, UserDTO.class));
        return userDTOPage;
    }

    @Override
    public List<UserDTO> searchUsers(String query) {
        List<User> users = userRepository.searchUsers(query);
        List<UserDTO> userDTOS = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public List<UserDTO> getAlls() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = listUser
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails;
        }
        return null;
    }
}
