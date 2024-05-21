package com.ProvaGrupo.SpringEcommerce.repository;

import com.ProvaGrupo.SpringEcommerce.model.ShoppingCart;
import com.ProvaGrupo.SpringEcommerce.model.ShoppingCartItemTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    private Faker faker = new Faker(new Locale("en-US"));
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartItemTest.class);

    private ShoppingCart cart;


    @BeforeEach
    public void setup(){
        BigDecimal price = BigDecimal.valueOf(faker.random().nextDouble()).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(faker.random().nextInt(9999)));
        cart = ShoppingCart.builder()
                .id(null)
                .cartTotalPrice(price)
                .numberOfItems(faker.random().nextInt(999))
                .username(faker.name().username())
                .shoppingCartItems(null)
                .build();
        log.info("Setup complete. Generated shopping cart: {}", cart);
    }

    @Test
    public void testSaveAndFindById(){
        log.info("--- Running SaveAndFindById ---\n");
        ShoppingCart savedCart = cartRepository.save(cart);
        log.info("Saved cart: {}", savedCart);
        Optional<ShoppingCart> foundCart = cartRepository.findById(savedCart.getId());
        assertTrue(foundCart.isPresent());
        log.info("Found cart by ID: {}", foundCart.get());
        assertEquals(savedCart.getId(), foundCart.get().getId());

    }


}
