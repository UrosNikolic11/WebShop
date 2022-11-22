package com.consulteer.webShop.services;

import com.consulteer.webShop.dto.CreateRoleDto;
import com.consulteer.webShop.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleDto create(CreateRoleDto createRoleDto);
    Page<RoleDto> findAll(Pageable pageable);
    RoleDto findById(Long id);
    RoleDto update(Long id, CreateRoleDto createRoleDto);
    void remove(Long id);
}
