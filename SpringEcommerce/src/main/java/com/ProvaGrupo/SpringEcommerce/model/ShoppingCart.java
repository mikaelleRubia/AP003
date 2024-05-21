package com.ProvaGrupo.SpringEcommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "Cart total price should to be 0 or greater")
    @NotNull(message = "Cart total price shouldn't be null")
    private BigDecimal cartTotalPrice;

    @Min(value = 0, message = "Number of items should to be 0 or greater")
    private int numberOfItems;

    @NotBlank(message = "Username shouldn't be empty")
    @Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters")
    private String username;

    @OneToMany(mappedBy = "shoppingCart" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShoppingCartItem> shoppingCartItems;
}
