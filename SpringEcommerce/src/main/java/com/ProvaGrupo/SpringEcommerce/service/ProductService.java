package com.ProvaGrupo.SpringEcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ProvaGrupo.SpringEcommerce.controller.form.ProductForm;
import com.ProvaGrupo.SpringEcommerce.dto.ProductDto;
import com.ProvaGrupo.SpringEcommerce.model.Product;
import com.ProvaGrupo.SpringEcommerce.model.ProductAttribute;
import com.ProvaGrupo.SpringEcommerce.model.ProductRating;
import com.ProvaGrupo.SpringEcommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	
	@Transactional(readOnly = true)
	public List<ProductDto> findAll() {
	    return repository.findAll().stream()
	                     .map(ProductDto::new)
	                     .collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<ProductDto>findByName(String name) {
	    return repository.findByName(name).stream()
	                            .map(ProductDto::new)
	                            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
	    return repository.findById(id)
	             .map(ProductDto::new)
	             .orElseThrow(() -> new EntityNotFoundException("Product not found for id: " + id));
	}
	
	@Transactional
	public void delete(Long id) {
		Product Product= repository.findById(id)
	                                  .orElseThrow(() -> new EntityNotFoundException("Product not found for id: " + id));        
	    repository.deleteById(id);
	}
	
	@Transactional
	public ProductDto update(Long id, ProductForm productForm) {
	    Product entity = repository.findById(id)
	                            .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + id));

	    entity.setName(productForm.name());
	    entity.setDescription(productForm.description());
	    entity.setPrice(productForm.price());
	    entity.setSku(productForm.sku());
	    entity.setImageUrl(productForm.imageUrl());
	    entity.setCategory(productForm.category());
	    List<ProductAttribute> newProductAttributeList = productForm.productAttributeList();
	    
	    entity.getProductAttributeList().clear();
	    entity.getProductAttributeList().addAll(newProductAttributeList);
	    entity.setQuantity(productForm.quantity());
	    entity.setManufacturer(productForm.manufacturer());
	    entity.setFeatured(productForm.featured());
	    
	    List<ProductRating> newProductRating = productForm.productRating();
	    entity.getProductRating().clear();
	    entity.getProductRating().addAll(newProductRating);

	    
	    entity = repository.save(entity);
	    return new ProductDto(entity);
	}
	
	
	@Transactional
	public ProductDto insert(ProductForm productForm) {
	    Product entity = new Product();
	    entity.setName(productForm.name());
	    entity.setDescription(productForm.description());
	    entity.setPrice(productForm.price());
	    entity.setSku(productForm.sku());
	    entity.setImageUrl(productForm.imageUrl());
	    entity.setCategory(productForm.category());

	    List<ProductAttribute> newProductAttributeList = productForm.productAttributeList();
	    entity.setProductAttributeList(newProductAttributeList);

	    entity.setQuantity(productForm.quantity());
	    entity.setManufacturer(productForm.manufacturer());
	    entity.setFeatured(productForm.featured());

	    List<ProductRating> newProductRating = productForm.productRating();
	    entity.setProductRating(newProductRating);

	    entity = repository.save(entity);
	    return new ProductDto(entity);
	}
	
}
