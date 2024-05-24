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

import com.ProvaGrupo.SpringEcommerce.dto.CategoryDto;
import com.ProvaGrupo.SpringEcommerce.controller.form.CategoryForm;
import com.ProvaGrupo.SpringEcommerce.service.CategoryService;



@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("/get/")
	public ResponseEntity<List<CategoryDto>> findAll(@RequestParam(value = "name", required = false) String name) {
	    try {

	        List<CategoryDto> list;
	        list = (name != null && !name.isEmpty()) ? service.findByName(name) : service.findAll();
	        
	        if (list.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        }
            LOGGER.info("Executing category search operation.");
            return ResponseEntity.ok().body(list);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid search or sorting parameters: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryForm categoryFor) {
        try {
            CategoryDto categoryDto = service.update(id, categoryFor);
            LOGGER.info("Updating category: {}", categoryFor.name());
            return ResponseEntity.ok().body(categoryDto);
        } catch (Exception e) {
            LOGGER.error("Category not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        try {
            CategoryDto categoryDto = service.findById(id);
            LOGGER.info("Executing category search operation by ID: {}", id);
            return ResponseEntity.ok().body(categoryDto);
        } catch (Exception e) {
            LOGGER.error("Category not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
	}
	
	@PostMapping("/submit")
	public ResponseEntity<CategoryDto> insert(@RequestBody CategoryForm categoryFor, UriComponentsBuilder uriC) {
		
		try {
			CategoryDto categoryDto = service.insert(categoryFor);
			URI uri = uriC.path("/api/category/{id}").buildAndExpand(categoryDto.getId()).toUri();
			LOGGER.info("Inserting new category: {}", categoryFor.name());
			return ResponseEntity.created(uri).body(categoryDto);
			
        } catch (Exception e) {
        	LOGGER.error("Error inserting category: {}", e.getMessage());
            return ResponseEntity.notFound().build();
       }
	
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			LOGGER.info("Category with ID {} was successfully deleted.", id);
			return ResponseEntity.noContent().build();
			
        } catch (Exception e) {
        	LOGGER.error("Category not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
		
	
	}
	
//    @DeleteMapping("/delete/")
//    public ResponseEntity<Void> deleteCategoryNull() {
//        LOGGER.warn("Attempt to delete without specifying ID");
//        return ResponseEntity.badRequest().build();
//    }
//    
//    @PutMapping("/")
//    public ResponseEntity<Void> updateCategoryNull() {
//        LOGGER.warn("Attempt to update without specifying ID");
//        return ResponseEntity.badRequest().build();
//    }
	
}
