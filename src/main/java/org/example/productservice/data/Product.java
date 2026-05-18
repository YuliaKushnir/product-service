package org.example.productservice.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products",
    indexes = {
            @Index(name = "idx_products_name", columnList = "name"),
            @Index(name = "idx_products_gender", columnList = "gender")
    })
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private String description;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection(targetClass = Color.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            indexes = { @Index(name = "idx_product_colors_color", columnList = "color") }
    )
    @Column(name = "color")
    private List<Color> colors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(name = "idx_product_categories_product", columnList = "product_id"),
                    @Index(name = "idx_product_categories_category", columnList = "category_id")
            }
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Column(name = "material")
    private String material;


    private LocalDateTime createdAt;
}