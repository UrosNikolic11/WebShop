package com.consulteer.webShop.controllers;

import com.consulteer.webShop.dto.CreateRoleDto;
import com.consulteer.webShop.dto.RoleDto;
import com.consulteer.webShop.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('MANAGER')")
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody @Valid CreateRoleDto createRoleDto) {
        return new ResponseEntity<>(roleService.create(createRoleDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable("id") Long id
            , @RequestBody @Valid CreateRoleDto createRoleDto) {
        return new ResponseEntity<>(roleService.update(id, createRoleDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RoleDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(roleService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        roleService.remove(id);
    }
}
