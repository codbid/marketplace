package org.example.marketplace.app.seller.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.marketplace.app.seller.staff.model.SellerStaffEntity;
import org.example.marketplace.app.users.model.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Table(name = "sellers")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(unique = true, nullable = false)
    private String slug;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellerStatus status = SellerStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Setter
    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "seller")
    private List<SellerStaffEntity> staff;

     public SellerEntity(String name, String slug, UserEntity owner) {
        this.name = name;
        this.slug = slug;
        this.owner = owner;
    }
}
