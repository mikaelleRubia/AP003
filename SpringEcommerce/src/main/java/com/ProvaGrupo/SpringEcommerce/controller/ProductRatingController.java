package com.ProvaGrupo.SpringEcommerce.controller;

import static org.springframework.http.HttpStatus.OK;

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

    @PostMapping("/submit")
    public ResponseEntity<?> postRating(@RequestBody @Valid ProductRatingDto productRatingDto) {
    	return productRatingService.postProductRating(productRatingDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<?>  editRating(@RequestBody @Valid ProductRatingDto productRatingDto) {
    	return productRatingService.editProductRating(productRatingDto);
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<?>  deleteRating(@PathVariable String ratingId) {
    	return productRatingService.deleteProductRating(ratingId);
    }

    @GetMapping("/get/{sku}")
    public ResponseEntity<?> getRating(@PathVariable String sku) {
        return new ResponseEntity<>(productRatingService.getProductRating(sku), OK);
    }
}