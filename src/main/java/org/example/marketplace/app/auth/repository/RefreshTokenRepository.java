package org.example.marketplace.app.auth.repository;

import org.example.marketplace.app.auth.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Query(value = "UPDATE refresh_tokens SET revoked = true WHERE user_id = :userId", nativeQuery = true)
    int revokeAllForUser(@Param("userId") Long userId);
}
