package ecomerce.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE products SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String color;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String specifications;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(name = "is_best_sell", nullable = false)
    private Boolean isBestSell;

    @Column(name = "is_new", nullable = false)
    private Boolean isNew;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Boolean active;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}
