package org.example.marketplace.app.products.mapper;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.products.dto.ProductCommonInfo;
import org.example.marketplace.app.products.dto.StaffCreateProductRequest;
import org.example.marketplace.app.products.model.ProductEntity;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.users.mapper.UserMapper;
import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final UserMapper userMapper;

    public ProductEntity toProductEntity(StaffCreateProductRequest request, SellerEntity seller, UserEntity createdBy) {
        ProductEntity product = new ProductEntity(seller, createdBy);
        product.setName(request.name());
        product.setSlug("tmp-" + UUID.randomUUID());
        product.setDescription(request.description());
        product.setStock(request.stock());
        product.setPriceCents(request.priceCents());
        return product;
    }

    public ProductCommonInfo toCommonInfo(ProductEntity product) {
        return new ProductCommonInfo(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSlug(),
                product.getPriceCents(),
                product.getStock(),
                product.getSeller().getId(),
                userMapper.toShortInfo(product.getCreatedBy()),
                product.getCreatedAt().toString(),
                product.getUpdatedAt().toString()
        );
    }
}
