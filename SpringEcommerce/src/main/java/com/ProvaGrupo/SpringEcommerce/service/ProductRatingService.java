package com.ProvaGrupo.SpringEcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ProvaGrupo.SpringEcommerce.dto.ProductRatingDto;
import com.ProvaGrupo.SpringEcommerce.model.Product;
import com.ProvaGrupo.SpringEcommerce.model.ProductRating;
import com.ProvaGrupo.SpringEcommerce.repository.ProductRatingRepository;
import com.ProvaGrupo.SpringEcommerce.repository.ProductRepository;

import jakarta.validation.Valid;

@Service
public class ProductRatingService {

	@Autowired
	ProductRatingRepository productRatingRepository;

	@Autowired
	ProductRepository productRepository;

	public ResponseEntity<?> postProductRating(@Valid ProductRatingDto productRatingDto, String sku) {
		try {
			ProductRating productRating = productRatingDto.toProductRating(productRatingDto);

			Product product = productRepository.findBySku(sku)
					.orElseThrow(() -> new IllegalArgumentException("Product not found with the provided SKU"));
			productRating.setProduct(product);

			ProductRating savedRating = productRatingRepository.save(productRating);

			product.getProductRating().add(savedRating);

			productRepository.save(product);

			return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error creating product rating ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> getProductRating(String sku) {
		try {
			Product product = productRepository.findBySku(sku)
					.orElseThrow(() -> new IllegalArgumentException("Product not found with the provided SKU"));

			return new ResponseEntity<>(product.getProductRating(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error retrieving product reviews", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> editProductRating(@Valid ProductRatingDto productRatingDto, Long id) {
	    try {
	        ProductRating existingRating = productRatingRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Product review not found"));

	        existingRating.setRatingStars(productRatingDto.ratingStars());
	        existingRating.setReview(productRatingDto.review());
	        existingRating.setUserName(productRatingDto.userName());

	        ProductRating updatedRating = productRatingRepository.save(existingRating);

	        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error editing product review", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	public ResponseEntity<?> deleteProductRating(Long ratingId) {
		try {
			ProductRating productRating = productRatingRepository.findById(ratingId)
					.orElseThrow(() -> new IllegalArgumentException("Product review not found"));
			productRatingRepository.delete(productRating);
			
			return new ResponseEntity<>("Product review successfully deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting product review", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
