package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto create(CreateUserDto createUserDto);
    Page<UserDto> findAll(Pageable pageable);
    UserDto findById(Long id);
    UserDto update(Long id, CreateUserDto createUserDto);
    void remove(Long id);
}
