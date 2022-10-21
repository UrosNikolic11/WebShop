package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto create(CreateUserDto createUserDto);
    Page<UserDto> findAll(Pageable pageable);
    UserDto findById(Long id);
    UserDto update(Long id, CreateUserDto createUserDto);
    void remove(Long id);
}
