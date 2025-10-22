package org.example.marketplace.app.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.marketplace.app.users.model.UserRole;

public record UserCreateRequest (
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotBlank String name,
    @NotNull UserRole role
) {}
