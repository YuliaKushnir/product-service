package org.example.productservice.dto.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    private Long id;
    private Long productId;
    private String color;
    private String size;
    private Integer quantity;
}