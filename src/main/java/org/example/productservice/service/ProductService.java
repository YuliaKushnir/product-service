package org.example.productservice.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.productservice.dto.FilteredProductResponse;
import org.example.productservice.dto.ProductDto;
import org.example.productservice.dto.ProductQueryDto;
import org.example.productservice.dto.SaveProductDto;

import java.util.List;

/**
 * Service Interface for product operations.
 */
public interface ProductService {

    ProductDto addProduct(@Valid SaveProductDto saveProductDto);

    ProductDto getProductById(Long id);

    ProductDto updateProduct(@NotNull @Min(1) Long id, @Valid SaveProductDto saveProductDto);

    void deleteProduct(Long id);

    FilteredProductResponse search(ProductQueryDto productQueryDto);

    List<ProductDto> searchProducts(String query);
}
