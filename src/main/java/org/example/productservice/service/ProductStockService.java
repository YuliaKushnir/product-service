package org.example.productservice.service;

import org.example.productservice.dto.stock.SaveStockDto;
import org.example.productservice.dto.stock.StockDto;
import org.example.productservice.dto.stock.StockViewDto;

import java.util.List;

/**
 * Service Interface for stock operations.
 */
public interface ProductStockService {

    StockDto addOrUpdateStock(SaveStockDto dto);

    List<StockDto> getStockByProduct(Long productId);

    StockDto increment(Long stockId, int delta);

    StockDto decrement(Long stockId, int delta);

    List<StockViewDto> getAllStock();
}