package org.example.marketplace.app.users.DTO;

import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.model.UserRole;

public record UserFullInfo(Long id, String email, String name, UserRole role, String createdAt, String updatedAt
) {
    public UserFullInfo(UserEntity entity) {
        this(entity.getId(), entity.getEmail(), entity.getName(), entity.getRole(), entity.getCreatedAt().toString(), entity.getUpdatedAt().toString());
    }
}

