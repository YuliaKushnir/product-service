package org.example.productservice.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockViewDto {

    private String productName;
    private Color color;
    private Size size;
    private Integer quantity;
}