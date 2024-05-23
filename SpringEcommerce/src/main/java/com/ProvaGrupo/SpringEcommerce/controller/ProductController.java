package com.ProvaGrupo.SpringEcommerce.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ProvaGrupo.SpringEcommerce.controller.form.ProductForm;
import com.ProvaGrupo.SpringEcommerce.dto.ProductDto;
import com.ProvaGrupo.SpringEcommerce.service.ProductService;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll(@RequestParam(value = "name", required = false) String name) {
	    try {
	        List<ProductDto> list;
	        list = (name != null && !name.isEmpty()) ? service.findByName(name) : service.findAll();

	        if (list.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
            LOGGER.info("Executing Product search operation.");
            return ResponseEntity.ok().body(list);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid search or sorting parameters: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductForm productForm) {
        try {
            ProductDto productDto = service.update(id, productForm);
            LOGGER.info("Updating product: {}", productForm.name());
            return ResponseEntity.ok().body(productDto);
        } catch (Exception e) {
            LOGGER.error("product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        try {
            ProductDto productDto = service.findById(id);
            LOGGER.info("Executing product search operation by ID: {}", id);
            return ResponseEntity.ok().body(productDto);
        } catch (Exception e) {
            LOGGER.error("product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> insert(@RequestBody ProductForm productForm, UriComponentsBuilder uriC) {
		try {
			ProductDto productDto = service.insert(productForm);
			URI uri = uriC.path("/api/product/{id}").buildAndExpand(productDto.getId()).toUri();
			LOGGER.info("Inserting new product: {}", productDto.getId());
			return ResponseEntity.created(uri).body(productDto);
        } catch (Exception e) {
        	LOGGER.error("Error inserting produc: {}", e.getMessage());
            return ResponseEntity.notFound().build();
       }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			LOGGER.info("product with ID {} was successfully deleted.", id);
			return ResponseEntity.noContent().build();
			
        } catch (Exception e) {
        	LOGGER.error("Category not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
	}
	
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteCategoryNull() {
        LOGGER.warn("Attempt to delete without specifying ID");
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/")
    public ResponseEntity<Void> updateCategoryNull() {
        LOGGER.warn("Attempt to update without specifying ID");
        return ResponseEntity.badRequest().build();
    }
}
