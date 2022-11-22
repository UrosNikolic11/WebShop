package com.consulteer.webShop.mappers;

import com.consulteer.webShop.dto.CreateRoleDto;
import com.consulteer.webShop.dto.RoleDto;
import com.consulteer.webShop.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleMapper() {
    }

    public RoleDto map(Role role){
        return new RoleDto(role.getId(), role.getRole());
    }

    public Role map(CreateRoleDto createRoleDto){
        return new Role(null, createRoleDto.role());
    }
}
