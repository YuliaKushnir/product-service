package org.example.productservice.service;

import org.example.productservice.dto.RequestCategoryDto;
import org.example.productservice.dto.ResponseCategoryDto;

import java.util.List;

/**
 * Service Interface for category operations.
 */
public interface CategoryService {
    List<ResponseCategoryDto> getAllCategories();

    ResponseCategoryDto addCategory(RequestCategoryDto requestCategoryDto);

    void deleteCategory(Long id);

    ResponseCategoryDto getCategoryById(Long id);
}
