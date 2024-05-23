package com.ProvaGrupo.SpringEcommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ProvaGrupo.SpringEcommerce.model.Category;
import com.ProvaGrupo.SpringEcommerce.model.Product;
import com.ProvaGrupo.SpringEcommerce.model.ProductAttribute;
import com.ProvaGrupo.SpringEcommerce.model.ProductRating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private String imageUrl;
    private Category category;
    private List<ProductAttribute> productAttributeList;
    private Integer quantity;
    private String manufacturer;
    private boolean featured;
    private List<ProductRating> productRating;
    
    
	public ProductDto(Product product ) {
		super();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.sku = product.getSku();
		this.imageUrl = product.getImageUrl();
		this.category = product.getCategory();
		this.quantity = product.getQuantity();
		this.manufacturer = product.getManufacturer();
		this.featured = product.getFeatured();
		this.productRating = product.getProductRating();
	}
    
    
    
}
