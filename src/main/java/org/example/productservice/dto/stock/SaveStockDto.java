package org.example.productservice.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveStockDto {

    @NotNull
    private Long productId;

    @NotNull
    private Color color;

    @NotNull
    private Size size;

    @NotNull
    @Min(0)
    private Integer quantity;
}