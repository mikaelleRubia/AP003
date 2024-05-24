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

	public ResponseEntity<?> postProductRating(@Valid ProductRatingDto productRatingDto) {
		try {
            ProductRating productRating = productRatingDto.toProductRating(productRatingDto);
            
            Product product = productRepository.findBySku(productRatingDto.sku())
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
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o SKU fornecido"));
            
            return new ResponseEntity<>(product.getProductRating(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao recuperar as avaliações do produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	public ResponseEntity<?> editProductRating(@Valid ProductRatingDto productRatingDto) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	public ResponseEntity<?> deleteProductRating(String ratingId) {
		// TODO Stub de método gerado automaticamente
		return null;
	}



}
