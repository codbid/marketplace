package org.example.marketplace.app.products.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.access.service.AccessService;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.products.dto.ProductCommonInfo;
import org.example.marketplace.app.products.dto.StaffCreateProductRequest;
import org.example.marketplace.app.products.service.ProductStaffService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductStaffService productStaffService;
    private final AccessService accessService;

    @PostMapping("/seller/{sellerId}/new")
    public ProductCommonInfo addProduct(
            @PathVariable Long sellerId,
            @Valid @RequestBody StaffCreateProductRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        accessService.requireSellerStaffAccess(sellerId, principal);

        return productStaffService.createProduct(sellerId, request, principal);
    }
}
