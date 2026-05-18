package org.example.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.productservice.data.Product;
import org.example.productservice.data.ProductStock;
import org.example.productservice.dto.stock.SaveStockDto;
import org.example.productservice.dto.stock.StockDto;
import org.example.productservice.dto.stock.StockViewDto;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.repository.ProductStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

    private final ProductStockRepository stockRepository;
    private final ProductRepository productRepository;

    @Override
    public StockDto addOrUpdateStock(SaveStockDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductStock stock = stockRepository
                .findByProductIdAndColorAndSize(dto.getProductId(), dto.getColor(), dto.getSize())
                .orElse(null);

        if (stock == null) {
            stock = new ProductStock();
            stock.setProduct(product);
            stock.setColor(dto.getColor());
            stock.setSize(dto.getSize());
            stock.setQuantity(dto.getQuantity());
        } else {
            stock.setQuantity(stock.getQuantity() + dto.getQuantity());
        }

        ProductStock saved = stockRepository.save(stock);

        return map(saved);
    }

    @Override
    public List<StockDto> getStockByProduct(Long productId) {
        return stockRepository.findByProductId(productId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public StockDto increment(Long stockId, int delta) {
        ProductStock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));

        stock.setQuantity(stock.getQuantity() + delta);

        return map(stockRepository.save(stock));
    }

    @Override
    public StockDto decrement(Long stockId, int delta) {
        ProductStock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));

        int newQty = stock.getQuantity() - delta;

        if (newQty < 0) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + stock.getQuantity());
        }
        stock.setQuantity(newQty);

        ProductStock saved = stockRepository.save(stock);
        return map(saved);
    }

    public List<StockViewDto> getAllStock() {
        return stockRepository.findAllStock();
    }

    private StockDto map(ProductStock stock) {
        return new StockDto(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getColor() != null ? stock.getColor().name() : null,
                stock.getSize() != null ? stock.getSize().name() : null,
                stock.getQuantity()
        );
    }
}