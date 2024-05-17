package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "Cart total price should to be 0 or greater")
    private BigDecimal cartTotalPrice;

    @Min(value = 0, message = "Number of items should to be 0 or greater")
    private int numberOfItems;

    @NotEmpty(message = "Username shouldn't be empty")
    private String username;

    @OneToMany(mappedBy = "shoppingCart" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShoppingCartItem> shoppingCartItems;
}
