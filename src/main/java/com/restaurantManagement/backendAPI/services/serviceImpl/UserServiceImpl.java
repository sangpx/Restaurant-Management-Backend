package com.restaurantManagement.backendAPI.services.serviceImpl;

import com.restaurantManagement.backendAPI.exceptions.UserAlreadyExistException;
import com.restaurantManagement.backendAPI.exceptions.UserNotFoundException;
import com.restaurantManagement.backendAPI.models.dto.catalog.user.UserDTO;
import com.restaurantManagement.backendAPI.models.entity.User;
import com.restaurantManagement.backendAPI.repository.UserRepository;
import com.restaurantManagement.backendAPI.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

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
            throw new UserAlreadyExistException("A user with " + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhone(user.getPhone());
        user.setEmail(user.getEmail());
        user.setGender(user.getGender());
        user.setStatus(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public User update(User user, Long id) {
        User userFind = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        userFind.setUsername(user.getUsername());
        userFind.setPhone(user.getPhone());
        userFind.setEmail(user.getEmail());
        userFind.setGender(user.getGender());
        userFind.setStatus(true);
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
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
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
}
