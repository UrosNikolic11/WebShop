package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.exception.NotFoundException;
import com.consulteer.webShop.mappers.UserMapper;
import com.consulteer.webShop.model.User;
import com.consulteer.webShop.repositories.CartRepository;
import com.consulteer.webShop.repositories.UserRepository;
import com.consulteer.webShop.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartRepository = cartRepository;
    }

    @Override
    public UserDto create(CreateUserDto createUserDto) {
        User user = userMapper.map(createUserDto);
        userRepository.save(user);

        cartRepository.create(user);

        return userMapper.map(user);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::map);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        return userMapper.map(user);
    }

    @Override
    public UserDto update(Long id, CreateUserDto createUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User with given id does not exist!"));

        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());

        userRepository.save(user);

        return userMapper.map(user);
    }

    @Override
    public void remove(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User with given id does not exist!"));
        userRepository.delete(user);
    }
}
