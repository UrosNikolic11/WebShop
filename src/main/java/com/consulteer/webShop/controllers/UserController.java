package com.consulteer.webShop.controllers;

import com.consulteer.webShop.dto.CreateUserDto;
import com.consulteer.webShop.dto.UserDto;
import com.consulteer.webShop.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.create(createUserDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id
            , @RequestBody @Valid CreateUserDto createUserDto) {
        return new ResponseEntity<>(userService.update(id, createUserDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        userService.remove(id);
    }
}
