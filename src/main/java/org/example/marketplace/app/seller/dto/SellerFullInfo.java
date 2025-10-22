package org.example.marketplace.app.seller.dto;

import org.example.marketplace.app.seller.model.SellerStatus;
import org.example.marketplace.app.users.dto.UserFullInfo;

public record SellerFullInfo(
        Long id,
        UserFullInfo owner,
        String name,
        String slug,
        SellerStatus status,
        String createdAt,
        String updatedAt
) {}
