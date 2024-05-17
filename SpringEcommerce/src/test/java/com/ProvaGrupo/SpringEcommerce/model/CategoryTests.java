package com.ProvaGrupo.SpringEcommerce.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.javafaker.Faker;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


@SpringBootTest
public class CategoryTests {
	private static final Faker faker = new Faker(new Locale("py-br"));
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryTests.class);
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	
    @Test
    public void testCreateCategory() {
        String name = faker.commerce().department();
        List<String> facets = new ArrayList<>(); 

        Category category = new Category(null, name, facets);
        LOGGER.info("--------Executando testCreateCategory--------");
        assertNull(category.getId());
        assertEquals(name, category.getName());
        assertTrue(category.getPossibleFacets().isEmpty());
    }
	
    
    @Test
    public void testSettersCategory() {
        String name = faker.commerce().department();
        List<String> facets = new ArrayList<>(); 

        Category category = new Category();
        category.setName(name);
        category.setPossibleFacets(facets);
        LOGGER.info("--------Executando testSettersCategory--------");

        assertEquals(name, category.getName());
        assertTrue(category.getPossibleFacets().isEmpty());
    }
    
    @Test
    public void testValidationNameNull() {
    	Category category = new Category();
    	category.setName(null);

        var violations = validator.validate(category);

        assertEquals(1, violations.size());
        ConstraintViolation<Category> violation = violations.iterator().next();
        LOGGER.info("--------Executando  testValidationNameNull --------");
        assertEquals("Valor do campo name não pode ser null ou vazio", violation.getMessage());
    }
	
}
