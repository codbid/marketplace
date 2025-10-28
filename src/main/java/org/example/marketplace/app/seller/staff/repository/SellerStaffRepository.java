package org.example.marketplace.app.seller.staff.repository;

import org.example.marketplace.app.seller.staff.model.SellerStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SellerStaffRepository extends JpaRepository<SellerStaffEntity, Long> {
    boolean existsBySellerIdAndUserId(Long sellerId, Long userId);

    @Query("""
        select s
        from SellerStaffEntity s
        join s.user u
        where s.seller.id = :sellerId
        and u.isActive = true
    """)
    List<SellerStaffEntity> findAllActiveStaffBySellerId(Long sellerId);

    Optional<SellerStaffEntity> findBySellerIdAndUserId(Long sellerId, Long userId);
}
