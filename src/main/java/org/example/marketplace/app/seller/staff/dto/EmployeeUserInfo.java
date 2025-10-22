package org.example.marketplace.app.seller.staff.dto;

import org.example.marketplace.app.seller.staff.model.SellerStaffRole;
import org.example.marketplace.app.users.dto.UserFullInfo;

public record EmployeeUserInfo(
    Long id,
    Long sellerId,
    UserFullInfo user,
    SellerStaffRole role,
    String employedAt
) {}
