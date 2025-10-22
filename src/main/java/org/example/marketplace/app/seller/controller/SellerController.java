package org.example.marketplace.app.seller.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.access.service.AccessService;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.seller.dto.SellerCreateRequest;
import org.example.marketplace.app.seller.dto.SellerFullInfo;
import org.example.marketplace.app.seller.dto.SellerShortInfo;
import org.example.marketplace.app.seller.service.SellerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
@PreAuthorize("isAuthenticated()")
public class SellerController {
    private final SellerService sellerService;

    private final AccessService accessService;

    @GetMapping("/{id}")
    public SellerShortInfo getShortInfo(@PathVariable Long id) {
        return sellerService.getSellerShortInfo(id);
    }

    @PostMapping("/new") // VERIFIED
    public SellerFullInfo createSeller(@Valid @RequestBody SellerCreateRequest request,
                                       @AuthenticationPrincipal UserPrincipal principal) {
        accessService.requireVerification(principal);

        return sellerService.createSeller(request, principal);
    }
}
