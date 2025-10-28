package org.example.marketplace.app.products.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.marketplace.app.seller.model.SellerEntity;
import org.example.marketplace.app.users.model.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    @Setter
    @Column(nullable = false, length = 200)
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private String slug;

    @Setter
    @Column
    private String description;

    @Setter
    @Column(name = "price_cents", nullable = false)
    private Integer priceCents;

    @Setter
    @Column(nullable = false)
    private Integer stock;

    @Setter
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public ProductEntity(SellerEntity seller, UserEntity createdBy) {
        this.seller = seller;
        this.createdBy = createdBy;
    }

}
