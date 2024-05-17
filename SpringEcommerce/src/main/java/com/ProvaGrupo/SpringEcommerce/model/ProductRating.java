package com.ProvaGrupo.SpringEcommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class ProductRating {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Rating is required")
    @Min(value = 1, message = "Product rating must be between 1 and 5")
    @Max(value = 5, message = "Product rating must be between 1 and 5")
    private BigDecimal ratingStars;
    
    @NotNull(message = "Product id is required")
    private Long productId;
    
//    @NotBlank(message = "Elastic Search product id is required")
//    private String elasticSearchProductId;
    
    private String review;
    
    @NotBlank(message = "User name is required")
    @Size(min = 5, max = 16, message = "User name needs to be at least 5 characters long and at most 16 characters long")
    private String userName;
}