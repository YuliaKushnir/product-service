package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.FilteredProductResponse;
import org.example.productservice.dto.ProductDto;
import org.example.productservice.dto.ProductQueryDto;
import org.example.productservice.dto.SaveProductDto;
import org.example.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@Valid @ModelAttribute SaveProductDto saveProductDto){
        return productService.addProduct(saveProductDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @ModelAttribute SaveProductDto saveProductDto) {
        return productService.updateProduct(id, saveProductDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/_list")
    @ResponseStatus(HttpStatus.OK)
    public FilteredProductResponse getProductPage(@RequestBody ProductQueryDto productQueryDto) {
        return productService.search(productQueryDto);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> search(@RequestParam String query) {
        return productService.searchProducts(query);
    }

}
