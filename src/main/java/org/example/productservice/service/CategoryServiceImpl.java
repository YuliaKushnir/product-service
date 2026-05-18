package org.example.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.productservice.data.Category;
import org.example.productservice.dto.RequestCategoryDto;
import org.example.productservice.dto.ResponseCategoryDto;
import org.example.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public List<ResponseCategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new ResponseCategoryDto(category.getId(), category.getName()))
                .toList();
    }

    public ResponseCategoryDto addCategory(RequestCategoryDto requestCategoryDto) {
        Category category = new Category();
        category.setName(requestCategoryDto.getName());
        Category saved = categoryRepository.save(category);
        return new ResponseCategoryDto(saved.getId(), saved.getName());
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found for id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public ResponseCategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id));
        return new ResponseCategoryDto(category.getId(), category.getName());
    }
}
