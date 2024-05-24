package com.ProvaGrupo.SpringEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProvaGrupo.SpringEcommerce.dto.ProductRatingDto;
import com.ProvaGrupo.SpringEcommerce.service.ProductRatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/ratings")
public class ProductRatingController {

	@Autowired
    private ProductRatingService productRatingService;

    @PostMapping("/submit/{sku}")
    public ResponseEntity<?> postRating(@RequestBody @Valid ProductRatingDto productRatingDto, @PathVariable String sku) {
    	return productRatingService.postProductRating(productRatingDto, sku);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?>  editRating(@RequestBody @Valid ProductRatingDto productRatingDto, @PathVariable Long id) {
    	return productRatingService.editProductRating(productRatingDto, id);
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<?>  deleteRating(@PathVariable Long ratingId) {
    	return productRatingService.deleteProductRating(ratingId);
    }

    @GetMapping("/get/{sku}")
    public ResponseEntity<?> getRating(@PathVariable String sku) {
        return productRatingService.getProductRating(sku);
    }
}