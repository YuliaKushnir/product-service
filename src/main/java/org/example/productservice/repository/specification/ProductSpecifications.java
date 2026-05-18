package org.example.productservice.repository.specification;

import org.example.productservice.data.Product;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecifications {
    /** фільтрація по категоріях */
    public static Specification<Product> hasCategories(List<String> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return null;
            }
            return root.join("categories").get("name").in(categories);
        };
    }

    /** Фільтрація по кольорах */
    public static Specification<Product> hasColors(List<Color> colors) {
        return (root, query, criteriaBuilder) -> {
            if (colors == null || colors.isEmpty()) {
                return null;
            }
            return root.join("colors").in(colors);
        };
    }
    /** Фільтрація по матеріалу */
    public static Specification<Product> hasMaterial(String material) {
        return (root, query, cb) -> {
            if (material == null || material.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("material"), material);
        };
    }

    /** Фільтрація по статі */
    public static Specification<Product> hasGender(Gender gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("gender"), gender);
        };
    }
}
