package org.example.marketplace.app.seller.staff.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.users.model.UserEntity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seller_staff")
public class SellerStaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SellerStaffRole role = SellerStaffRole.OPERATOR;

    @CreationTimestamp
    @Column(name = "employed_at", nullable = false)
    private Instant employedAt;

    public SellerStaffEntity(SellerEntity seller, UserEntity user) {
        this.seller = seller;
        this.user = user;
    }
}
