package org.example.marketplace.app.users.dto;

import org.example.marketplace.app.users.model.UserRole;

public record UserFullInfo(Long id, String email, String name, UserRole role, String createdAt, String updatedAt) {}

