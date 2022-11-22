package com.consulteer.webShop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class CreateUserDto {
    @NotBlank
    private final String username;
    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
    @NotNull
    private final Long roleId;

    public CreateUserDto(String username, String firstName, String lastName, String email, String password, Long roleId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getUsername() {
        return username;
    }
}
