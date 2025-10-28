package org.example.marketplace.app.products.service;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.auth.security.UserPrincipal;
import org.example.marketplace.app.products.dto.ProductCommonInfo;
import org.example.marketplace.app.products.dto.StaffCreateProductRequest;
import org.example.marketplace.app.products.mapper.ProductMapper;
import org.example.marketplace.app.products.model.ProductEntity;
import org.example.marketplace.app.products.repository.ProductRepository;
import org.example.marketplace.app.seller.exception.SellerException;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.seller.repository.SellerRepository;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStaffService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    private final Slugify slugify;

    @Transactional
    public ProductCommonInfo createProduct(Long sellerId, StaffCreateProductRequest request, UserPrincipal principal) {
        SellerEntity seller = sellerRepository.findAvailableForStaffById(sellerId)
                .orElseThrow(() -> new ApiException(SellerException.seller_not_found));

        UserEntity createdBy = userRepository.findById(principal.getId())
                .orElseThrow(() -> new ApiException(UserException.user_not_found));

        ProductEntity product = productMapper.toProductEntity(request, seller, createdBy);

        productRepository.save(product);

        String slug = slugify.slugify(product.getName()) + "-" + product.getId();
        product.setSlug(slug);

        return productMapper.toCommonInfo(product);
    }
}
