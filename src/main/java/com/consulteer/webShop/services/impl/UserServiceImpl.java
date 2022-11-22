package com.consulteer.webShop.services.impl;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.exception.NotFoundException;
import com.consulteer.webShop.mappers.UserMapper;
import com.consulteer.webShop.model.Role;
import com.consulteer.webShop.model.User;
import com.consulteer.webShop.repositories.CartRepository;
import com.consulteer.webShop.repositories.RoleRepository;
import com.consulteer.webShop.repositories.UserRepository;
import com.consulteer.webShop.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartRepository cartRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           CartRepository cartRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartRepository = cartRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserDto create(CreateUserDto createUserDto) {
        User user = userMapper.map(createUserDto);
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
        } catch (Exception ex) {
            throw new BadRequestException("Error with password hashing!");
        }
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
        Role role = roleRepository.findById(id).orElseThrow(() -> new BadRequestException("Role with given id does not exist!"));

        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        user.setRole(role);

        userRepository.save(user);

        return userMapper.map(user);
    }

    @Override
    public void remove(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BadRequestException("User with given id does not exist!"));
        userRepository.delete(user);
    }
}
