package org.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilteredProductResponse {
    private List<ProductDto> productDtoList;
    private int totalPages;
    private long totalElements;
}
