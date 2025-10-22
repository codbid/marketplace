package org.example.marketplace.app.users.mapper;

import org.example.marketplace.app.users.dto.UserCreateRequest;
import org.example.marketplace.app.users.dto.UserFullInfo;
import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserFullInfo toFullInfo(UserEntity entity) {
        return new UserFullInfo(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getRole(),
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt().toString()
        );
    }

    public UserEntity toUserEntity(UserCreateRequest request) {
        return new UserEntity(
                request.email(),
                request.name(),
                request.role()
        );
    }
}
