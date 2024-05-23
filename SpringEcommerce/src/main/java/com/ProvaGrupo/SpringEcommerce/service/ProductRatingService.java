package com.ProvaGrupo.SpringEcommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ProvaGrupo.SpringEcommerce.dto.ProductRatingDto;

import jakarta.validation.Valid;

@Service
public class ProductRatingService {

	public ResponseEntity<?> postProductRating(@Valid ProductRatingDto productRatingDto) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	public ResponseEntity<?> editProductRating(@Valid ProductRatingDto productRatingDto) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	public ResponseEntity<?> deleteProductRating(String ratingId) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	public Object getProductRating(String sku) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

}
