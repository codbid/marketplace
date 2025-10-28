package org.example.marketplace.app.products.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record StaffCreateProductRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NonNull Integer priceCents,
        @NonNull Integer stock
) {}
