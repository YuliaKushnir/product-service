package org.example.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;

import java.util.List;
/**
 * DTO for requested product fields for filtering, page number and page size.
 */
@Getter
@Setter
public class ProductQueryDto implements ProductFilter {

    private List<String> categories;

    private List<Color> colors;

    private String material;

    private Gender gender;

    @NotNull(message = "page number is required")
    private Integer page;

    @NotNull(message = "page size is required")
    private Integer pageSize;
}
