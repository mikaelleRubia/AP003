package com.ProvaGrupo.SpringEcommerce.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ProvaGrupo.SpringEcommerce.model.ProductRating;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
public class ProductRatingRepositoryTest {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private ProductRating productRating;
    
    private Faker faker;

    @BeforeEach
    void setUp() throws Exception {
        log.info("Setting up test data");

    	faker = new Faker();
    	
    	productRating = new ProductRating();
        productRating.setRatingStars(new BigDecimal(faker.number().randomDouble(1, 1, 5)));
        productRating.setProductId(3L);
        productRating.setUserName(faker.regexify("[a-zA-Z0-9]{5,16}"));
    }

    @Test
    void saveProductRating_ShouldInsertAndReturnTheProductRating() {
        log.info("Testing saveProductRating: START");

        ProductRating savedProductRating = productRatingRepository.save(productRating);

        assertThat(savedProductRating).isNotNull();
        assertThat(savedProductRating.getId()).isNotNull();
        assertThat(savedProductRating.getRatingStars()).isEqualTo(productRating.getRatingStars());
        assertThat(savedProductRating.getProductId()).isEqualTo(productRating.getProductId());
        assertThat(savedProductRating.getReview()).isEqualTo(productRating.getReview());
        assertThat(savedProductRating.getUserName()).isEqualTo(productRating.getUserName());

        log.info("Testing saveProductRating: COMPLETED");
    }

    @Test
    void findByUserName_ReturnProductRatingIfUserNameExistsInDatabase() {
        log.info("Testing findByUserName: START");

        ProductRating savedProductRating = testEntityManager.persistFlushFind(productRating);

        Optional<ProductRating> foundProductRating = productRatingRepository.findByUserName(savedProductRating.getUserName());

        assertThat(foundProductRating).isPresent();
        assertThat(foundProductRating.get().getId()).isEqualTo(savedProductRating.getId());

        log.info("Testing findByUserName: COMPLETED");
    }

    @Test
    void findAll_ReturnAllProductRatings() {
        log.info("Testing findAll: START");

        productRatingRepository.save(productRating);

        List<ProductRating> allProductRatings = productRatingRepository.findAll();

        assertThat(allProductRatings.size()).isEqualTo(1);

        log.info("Testing findAll: COMPLETED");
    }

    @Test
    void delete_DeletesProductRatingFromDatabase() {
        log.info("Testing delete: START");

        ProductRating savedProductRating = testEntityManager.persistFlushFind(productRating);

        productRatingRepository.delete(savedProductRating);

        Optional<ProductRating> deletedProductRating = productRatingRepository.findById(savedProductRating.getId());
        assertTrue(deletedProductRating.isEmpty());

        log.info("Testing delete: COMPLETED");
    }
}