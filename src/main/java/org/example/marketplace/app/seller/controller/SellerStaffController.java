package org.example.marketplace.app.seller.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.access.service.AccessService;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.seller.service.SellerStaffService;
import org.example.marketplace.app.seller.staff.dto.EmployeeUserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/{sellerId}/staff")
@PreAuthorize("isAuthenticated()")
public class SellerStaffController {
    private final SellerStaffService sellerStaffService;

    private final AccessService accessService;

    @PostMapping("/user/{userId}") // OWNER
    public EmployeeUserInfo employeeStaff(@PathVariable Long sellerId,
                                         @PathVariable Long userId,
                                         @AuthenticationPrincipal UserPrincipal principal) {
        accessService.requireSellerOwnerAccess(sellerId, principal);

        return sellerStaffService.inviteUser(sellerId, userId);
    }

    @GetMapping("/list") // OWNER
    public List<EmployeeUserInfo> getAllStaffBySeller(@PathVariable Long sellerId,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        accessService.requireSellerOwnerAccess(sellerId, principal);

        return sellerStaffService.getAllActiveStaffBySellerId(sellerId);
    }

    @DeleteMapping("/user/{userId}") // OWNER
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStaff(@PathVariable Long sellerId,
                                         @PathVariable Long userId,
                                         @AuthenticationPrincipal UserPrincipal principal) {
        accessService.requireSellerOwnerAccess(sellerId, principal);

        sellerStaffService.deleteStaff(sellerId, userId);
    }
}
