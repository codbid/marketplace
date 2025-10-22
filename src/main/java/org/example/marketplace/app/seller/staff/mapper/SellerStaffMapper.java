package org.example.marketplace.app.seller.staff.mapper;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.seller.staff.dto.EmployeeUserInfo;
import org.example.marketplace.app.seller.staff.model.SellerStaffEntity;
import org.example.marketplace.app.users.mapper.UserMapper;
import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerStaffMapper {
    private final UserMapper userMapper;

    public SellerStaffEntity toEntity(SellerEntity seller, UserEntity user) {
        return new SellerStaffEntity(seller, user);
    }

    public EmployeeUserInfo toEmployeeUserInfo(SellerStaffEntity sellerStaff) {
        return new EmployeeUserInfo(
                sellerStaff.getId(),
                sellerStaff.getSeller().getId(),
                userMapper.toFullInfo(sellerStaff.getUser()),
                sellerStaff.getRole(),
                sellerStaff.getEmployedAt().toString()
        );
    }
}
