package org.example.marketplace.app.users.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.model.UserRole;

public record UserCreateRequest (
    @NotBlank @Email String email,
    @NotBlank String name,
    @NotBlank UserRole role
) {
    public UserEntity toEntity() {
        return new UserEntity(this.email, this.name, role);
    }
}
