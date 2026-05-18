package org.example.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.productservice.data.Category;
import org.example.productservice.data.Image;
import org.example.productservice.data.Product;
import org.example.productservice.dto.FilteredProductResponse;
import org.example.productservice.dto.ProductDto;
import org.example.productservice.dto.ProductQueryDto;
import org.example.productservice.dto.SaveProductDto;
import org.example.productservice.exceptions.CreationException;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.repository.specification.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    /** Додавання продукту */
    @Override
    public ProductDto addProduct(SaveProductDto saveProductDto) {

        Product product = new Product();

        product.setName(saveProductDto.getName());
        product.setDescription(saveProductDto.getDescription());
        product.setPrice(saveProductDto.getPrice());
        product.setGender(saveProductDto.getGender());
        product.setMaterial(saveProductDto.getMaterial());
        product.setCreatedAt(LocalDateTime.now());

        product.setColors(
                saveProductDto.getColors() != null
                        ? new ArrayList<>(saveProductDto.getColors())
                        : new ArrayList<>()
        );

        List<Category> categories = categoryRepository.findAllById(saveProductDto.getCategoryIds());
        product.setCategories(new ArrayList<>(categories));
        product.setImages(new ArrayList<>(processImages(saveProductDto.getImages(), product)));

        Product createdProduct = productRepository.save(product);

        if (createdProduct.getId() == null) {
            throw new CreationException("Failed to add product");
        }

        return mapProductToProductDto(createdProduct);
    }

    /** Отримання продукту */
    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for id: " + id));

        return mapProductToProductDto(product);
    }

    /** Оновлення продукту */
    @Override
    public ProductDto updateProduct(Long id, SaveProductDto saveProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found for id: " + id));

        product.setName(saveProductDto.getName());
        product.setPrice(saveProductDto.getPrice());
        product.setDescription(saveProductDto.getDescription());
        product.setGender(saveProductDto.getGender());
        product.setMaterial(saveProductDto.getMaterial());

        product.getColors().clear();
        if (saveProductDto.getColors() != null) {
            product.getColors().addAll(saveProductDto.getColors());
        }

        List<Category> categories = categoryRepository.findAllById(saveProductDto.getCategoryIds());

        product.getCategories().clear();
        product.getCategories().addAll(categories);

        if (saveProductDto.getImages() != null && !saveProductDto.getImages().isEmpty()) {
            product.getImages().clear();

            List<Image> newImages = processImages(saveProductDto.getImages(), product);
            product.getImages().addAll(newImages);
        }

        Product updatedProduct = productRepository.save(product);

        if (updatedProduct.getId() == null) {
            throw new CreationException("Failed to update product");
        }

        return mapProductToProductDto(updatedProduct);
    }

    /** Видалення */
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found for id: " + id);
        }
        productRepository.deleteById(id);
    }

    /** Сторінка з фільтруванням */
    @Override
    public FilteredProductResponse search(ProductQueryDto productQueryDto) {
        Pageable pageable = PageRequest.of(
                productQueryDto.getPage(),
                productQueryDto.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Specification<Product> specification = Specification
                .where(ProductSpecifications.hasCategories(productQueryDto.getCategories()))
                .and(ProductSpecifications.hasColors(productQueryDto.getColors()))
                .and(ProductSpecifications.hasMaterial(productQueryDto.getMaterial()))
                .and(ProductSpecifications.hasGender(productQueryDto.getGender()));

        Page<ProductDto> productDtoPage = productRepository
                .findAll(specification, pageable)
                .map(this::mapProductToProductDto);

        return new FilteredProductResponse(
                productDtoPage.getContent(),
                productDtoPage.getTotalPages(),
                productDtoPage.getTotalElements()
        );
    }

    /** Пошук продуктів, назва яких містить вираз */
    public List<ProductDto> searchProducts(String query) {
        return productRepository
                .findByNameContainingIgnoreCase(query)
                .stream()
                .map(this::mapProductToProductDto)
                .toList();
    }

    /** Мапінг в дто */
    private ProductDto mapProductToProductDto(Product product) {
        List<String> categories = product.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        List<String> imageUrls = product.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                categories,
                imageUrls,
                product.getGender(),
                product.getColors(),
                product.getMaterial(),
                product.getCreatedAt().toString()
        );
    }

    /** Завантаження зображень на Cloudinary*/
    private List<Image> processImages(List<MultipartFile> files, Product product) {

        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        return files.stream()
                .map(file -> {
                    String url = imageService.upload(file);
                    return new Image(null, url, product);
                })
                .collect(Collectors.toList());
    }
}