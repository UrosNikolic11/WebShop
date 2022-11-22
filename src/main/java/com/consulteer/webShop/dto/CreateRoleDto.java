package com.consulteer.webShop.dto;

import javax.validation.constraints.NotBlank;

public record CreateRoleDto(@NotBlank String role) {
    public CreateRoleDto(String role) {
        this.role = role;
    }

    @Override
    public String role() {
        return role;
    }
}
