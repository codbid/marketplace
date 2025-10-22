package org.example.marketplace.app.seller.repository;

import org.example.marketplace.app.seller.model.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    @Query("""
        select s from SellerEntity s
        where s.id = :id
            and s.deletedAt is null
            and s.status <> org.example.marketplace.app.seller.model.SellerStatus.SUSPENDED
    """)
    Optional<SellerEntity> findAvailableForStaffById(Long id);

    @Query("""
        select s from SellerEntity s
        where s.id = :id
            and s.deletedAt is null
            and s.status = org.example.marketplace.app.seller.model.SellerStatus.ACTIVE
    """)
    Optional<SellerEntity> findVisibleById(Long id);
}
