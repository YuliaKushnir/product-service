package org.example.productservice.data;

import jakarta.persistence.*;
import lombok.*;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Size;

@Entity
@Table(
        name = "product_stock",
        indexes = {
                @Index(name = "idx_stock_product", columnList = "product_id"),
                @Index(name = "idx_stock_product_color_size", columnList = "product_id,color,size")
        },
        uniqueConstraints = { @UniqueConstraint(name = "uk_product_color_size", columnNames = {"product_id", "color", "size"})}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** товар */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** колір */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    /** розмір */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    /** кількість на складі */
    @Column(nullable = false)
    private Integer quantity;
}