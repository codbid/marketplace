package org.example.marketplace.app.seller.service;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.seller.dto.SellerCreateRequest;
import org.example.marketplace.app.seller.dto.SellerFullInfo;
import org.example.marketplace.app.seller.dto.SellerShortInfo;
import org.example.marketplace.app.seller.exception.SellerException;
import org.example.marketplace.app.seller.mapper.SellerMapper;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.seller.model.SellerStatus;
import org.example.marketplace.app.seller.repository.SellerRepository;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final SellerMapper sellerMapper;

    private final Slugify slugify;

    @Transactional
    public SellerFullInfo createSeller(SellerCreateRequest request, UserPrincipal principal) {
        UserEntity user = userRepository.findByIdAndIsActiveIsTrue(principal.getId())
                .orElseThrow(() -> new ApiException(UserException.user_not_found));

        SellerEntity seller = sellerMapper.toSellerEntity(request, user);
        seller = sellerRepository.save(seller);

        String slug = slugify.slugify(seller.getName()) + "-" + seller.getId();
        seller.setSlug(slug);

        return sellerMapper.toFullInfo(sellerRepository.save(seller));
    }

    @Transactional()
    public SellerShortInfo getSellerShortInfo(Long id) {
        SellerEntity seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ApiException(SellerException.seller_not_found));

        return sellerMapper.toShortInfo(seller);
    }
}
