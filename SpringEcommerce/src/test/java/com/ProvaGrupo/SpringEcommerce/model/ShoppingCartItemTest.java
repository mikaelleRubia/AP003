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
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartItemTest {
    private static  final Faker faker = new Faker(new Locale("pt-br"));
    private static final Logger log = LoggerFactory.getLogger(ShoppingCartItemTest.class);
    private static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void testValidShoppingCartItem(){
        ShoppingCartItem item = new ShoppingCartItem(1L, faker.commerce().productName(), new BigDecimal(faker.random().nextDouble()), null);
        Set<ConstraintViolation<ShoppingCartItem>> violations = validator.validate(item);
        log.info("--- Running ValidShoppingCartItem---\n");
        assertTrue(violations.isEmpty());
    }
}
