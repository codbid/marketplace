package org.example.marketplace.app.products.dto;

import org.example.marketplace.app.users.dto.UserShortInfo;

public record ProductCommonInfo(
    Long id,
    String name,
    String description,
    String slug,
    Integer priceCents,
    Integer stock,
    Long sellerId,
    UserShortInfo createdBy,
    String createdAt,
    String updatedAt
) {}
