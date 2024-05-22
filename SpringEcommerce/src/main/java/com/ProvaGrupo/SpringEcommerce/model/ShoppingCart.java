package com.ProvaGrupo.SpringEcommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    private int numberOfItems;

    @NotBlank(message = "Username shouldn't be empty")
    private String username;

    @ToString.Exclude
    @OneToMany(mappedBy = "shoppingCart" , cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShoppingCartItem> shoppingCartItems;
}