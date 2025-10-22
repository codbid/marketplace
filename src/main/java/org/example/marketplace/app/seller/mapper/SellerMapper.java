package org.example.marketplace.app.seller.mapper;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.seller.dto.SellerCreateRequest;
import org.example.marketplace.app.seller.dto.SellerFullInfo;
import org.example.marketplace.app.seller.dto.SellerShortInfo;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.users.mapper.UserMapper;
import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SellerMapper {
    private final UserMapper userMapper;

    public SellerShortInfo toShortInfo(SellerEntity entity) {
        return new SellerShortInfo(entity.getId(), entity.getName(), entity.getSlug());
    }

    public SellerFullInfo toFullInfo(SellerEntity entity) {
        return new SellerFullInfo(
                entity.getId(),
                userMapper.toFullInfo(entity.getOwner()),
                entity.getName(),
                entity.getSlug(),
                entity.getStatus(),
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt().toString());
    }

    public SellerEntity toSellerEntity(SellerCreateRequest request, UserEntity user) {
        return new SellerEntity(request.name(), "tmp-" + UUID.randomUUID(), user);
    }
}
