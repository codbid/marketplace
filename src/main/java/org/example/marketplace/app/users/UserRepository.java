package org.example.marketplace.app.users;

import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    @Query(value = "SELECT * FROM users ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<UserEntity> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);
}
