package org.example.marketplace.app.access.service;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.access.exception.AccessException;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.seller.exception.SellerException;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.seller.repository.SellerRepository;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    /**
     * Check-layout, for custom validation level
     */
    private boolean haveAdminAccess(UserPrincipal principal) {
        return principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

    private boolean haveVerification(UserPrincipal principal) {
        UserEntity user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new ApiException(UserException.user_not_found));
        return user.getVerified();
    }

    private boolean haveSellerOwnerAccess(Long sellerId, UserPrincipal principal) {
        SellerEntity seller = sellerRepository.findAvailableForStaffById(sellerId)
                .orElseThrow(() -> new ApiException(SellerException.seller_not_found));

        return seller.getOwner().getId().equals(principal.getId());
    }

    /**
     * Validation-layout
     */
    public void requireSellerOwnerAccess(Long sellerId, UserPrincipal principal) {
        if (!haveSellerOwnerAccess(sellerId, principal))
            throw new ApiException(AccessException.forbidden);
    }

    public void requireVerification(UserPrincipal principal) {
        if (!haveVerification(principal))
            throw new ApiException(AccessException.user_not_verified);
    }


}
