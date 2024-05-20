package com.ProvaGrupo.SpringEcommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Item name shouldn't be empty")
    private String name;

    @Positive(message = "Price should be greater than zero")
    @NotNull(message = "Price shouldn't be null")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "shoppingCartId")
    private ShoppingCart shoppingCart;
}