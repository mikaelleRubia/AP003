package com.ProvaGrupo.SpringEcommerce.model;

import com.github.javafaker.Faker;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest {
    private static final Faker faker = new Faker(new Locale("en-US"));
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartItemTest.class);
    private static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void testValidShoppingCart(){
        BigDecimal price = BigDecimal.valueOf(faker.random().nextDouble()).setScale(2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(faker.random().nextInt(9999)));
        ShoppingCart cart = new ShoppingCart(1L, price, faker.random().nextInt(999), faker.name().username(), null);
        Set<ConstraintViolation<ShoppingCart>> violations = validator.validate(cart);
        log.info("--- Running ValidShoppingCart ---\nCart: {}\n", cart);
        assertTrue(violations.isEmpty());
    }
}
