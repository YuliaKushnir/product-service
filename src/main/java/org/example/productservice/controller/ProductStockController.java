package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.stock.SaveStockDto;
import org.example.productservice.dto.stock.StockChangeDto;
import org.example.productservice.dto.stock.StockDto;
import org.example.productservice.dto.stock.StockViewDto;
import org.example.productservice.service.ProductStockService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class ProductStockController {

    private final ProductStockService stockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockDto addOrUpdate(@RequestBody @Valid SaveStockDto dto) {
        return stockService.addOrUpdateStock(dto);
    }

    @GetMapping("/product/{productId}")
    public List<StockDto> getByProduct(@PathVariable Long productId) {
        return stockService.getStockByProduct(productId);
    }

    @GetMapping("/by-product/{productId}")
    public List<StockDto> byProduct(@PathVariable Long productId) {
        return stockService.getStockByProduct(productId);
    }

    @PatchMapping("/{id}/increment")
    public StockDto inc(@PathVariable Long id, @RequestBody StockChangeDto dto) {
        return stockService.increment(id, dto.getDelta());
    }

    @PatchMapping("/{id}/decrement")
    public StockDto decrement(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return stockService.decrement(id, quantity);
    }

    @GetMapping("/all")
    public List<StockViewDto> getAll() {
        return stockService.getAllStock();
    }
}