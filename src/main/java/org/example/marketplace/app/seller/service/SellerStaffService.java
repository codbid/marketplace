package org.example.marketplace.app.seller.service;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.seller.exception.SellerException;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.seller.repository.SellerRepository;
import org.example.marketplace.app.seller.staff.dto.EmployeeUserInfo;
import org.example.marketplace.app.seller.staff.exception.SellerStaffException;
import org.example.marketplace.app.seller.staff.mapper.SellerStaffMapper;
import org.example.marketplace.app.seller.staff.model.SellerStaffEntity;
import org.example.marketplace.app.seller.staff.repository.SellerStaffRepository;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerStaffService {
    private final SellerStaffRepository sellerStaffRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final SellerStaffMapper sellerStaffMapper;

    @Transactional
    public EmployeeUserInfo inviteUser(Long sellerId, Long userId) {
        SellerEntity seller = sellerRepository.findAvailableForStaffById(sellerId)
                .orElseThrow(() -> new ApiException(SellerException.seller_not_found));

        UserEntity user = userRepository.findByIdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new ApiException(UserException.user_not_found));

        if (sellerStaffRepository.existsBySellerIdAndUserId(sellerId, userId))
            throw new ApiException(SellerStaffException.user_already_employed);

        SellerStaffEntity sellerStaff = sellerStaffMapper.toEntity(seller, user);

        return sellerStaffMapper.toEmployeeUserInfo(sellerStaffRepository.save(sellerStaff));
    }

    @Transactional
    public List<EmployeeUserInfo> getAllActiveStaffBySellerId(Long sellerId) {
        return sellerStaffRepository.findAllActiveStaffBySellerId(sellerId).stream()
                .map(sellerStaffMapper::toEmployeeUserInfo)
                .toList();
    }

    @Transactional
    public void deleteStaff(Long sellerId, Long userId) {
        SellerStaffEntity sellerStaff = sellerStaffRepository.findBySellerIdAndUserId(sellerId, userId)
                .orElseThrow(() -> new ApiException(SellerStaffException.user_already_employed));

        sellerStaffRepository.delete(sellerStaff);
    }
}
