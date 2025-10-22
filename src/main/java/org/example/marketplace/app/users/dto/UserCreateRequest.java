package org.example.marketplace.app.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest (
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotBlank String name
) {}
