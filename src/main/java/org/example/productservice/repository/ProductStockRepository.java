package org.example.productservice.repository;

import org.example.productservice.data.ProductStock;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Size;
import org.example.productservice.dto.stock.StockViewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    Optional<ProductStock> findById(Long id);

    Optional<ProductStock> findByProductIdAndColorAndSize(Long productId, Color color, Size size);

    List<ProductStock> findByProductId(Long productId);

    @Query("""
    SELECT new org.example.productservice.dto.stock.StockViewDto(p.name, s.color, s.size, s.quantity)
        FROM ProductStock s
        JOIN s.product p
    """)
    List<StockViewDto> findAllStock();



}
