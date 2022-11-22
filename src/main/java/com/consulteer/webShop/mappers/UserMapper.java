package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import com.consulteer.webShop.exception.BadRequestException;
import com.consulteer.webShop.model.Role;
import com.consulteer.webShop.model.User;
import com.consulteer.webShop.repositories.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto map(User user){
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().getRole());
    }

    public User map(CreateUserDto createUserDto){
        Role role = roleRepository.findById(createUserDto.getRoleId()).orElseThrow(() -> new BadRequestException("There is no role with given id!"));
        return new User(null, createUserDto.getUsername(), createUserDto.getFirstName(), createUserDto.getLastName(), createUserDto.getEmail(), createUserDto.getPassword(), role);
    }
}
