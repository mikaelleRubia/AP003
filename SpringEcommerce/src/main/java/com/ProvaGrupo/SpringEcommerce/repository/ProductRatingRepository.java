package com.ProvaGrupo.SpringEcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProvaGrupo.SpringEcommerce.model.ProductRating;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    Optional<ProductRating> findByUserName(String userName);

}