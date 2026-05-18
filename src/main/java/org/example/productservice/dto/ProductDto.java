package org.example.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private List<String> categories;
    private List<String> imageUrls;
    private Gender gender;
    private List<Color> colors;
    private String material;
    private String createdAt;
}
