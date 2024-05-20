package com.ProvaGrupo.SpringEcommerce.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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