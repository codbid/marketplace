package org.example.marketplace.app.seller.dto;

import jakarta.validation.constraints.NotBlank;

public record SellerCreateRequest(
        @NotBlank String name
) {}
