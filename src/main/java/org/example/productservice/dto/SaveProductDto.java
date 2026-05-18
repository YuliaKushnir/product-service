package org.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveProductDto {

    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must be less than 255 characters")
    private String name;

    @NotNull(message = "price is required")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "categories are required")
    @Size(min = 1, message = "at least one categories is required")
    private List<Long> categoryIds;

    @Size(max = 6, message = "at most 6 images may be added")
    private List<MultipartFile> images;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotNull
    @Size(min = 1, message = "at least one color is required")
    private List<Color> colors;

    private String material;
}

