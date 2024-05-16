package model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {
    @NotEmpty(message = "Item name don't should be empty")
    private String name;
    @Positive
    private BigDecimal price;
}
