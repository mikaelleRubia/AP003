package com.ProvaGrupo.SpringEcommerce.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ProvaGrupo.SpringEcommerce.model.Category;
import com.github.javafaker.Faker;



@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private Faker faker = new Faker();
	
	private Long existingId;
	private String name;
	private Category category;

	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;

		name = faker.commerce().productName();

		category = Category.builder()
				.name(name)
				.possibleFacets(new ArrayList<>())
				.build();
	}
	
	@Test
	public void deleteShoultDeleteObjWhenIdExists() {
		categoryRepository.deleteById(existingId);
		
		Optional<Category> result = categoryRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoincremmentwhenIdIsNull() {
		
		testEntityManager.persistFlushFind(category);
		
		Category categoryNew = category;
		categoryNew.setId(null);
		
		category = categoryRepository.save(categoryNew);
		Assertions.assertNotNull(categoryNew.getId());
		Assertions.assertEquals(existingId + 1 , categoryNew.getId());
	
	}
	
	@Test
	public void searchByIdWithReturnID(){
		
	    Optional<Category> result = Optional.ofNullable(categoryRepository.getReferenceById(existingId));
	    Assertions.assertTrue(result.isPresent()); 

	    Category category_ = result.get(); 
	    Assertions.assertNotNull(category_.getId());
	    Assertions.assertEquals(existingId, category_.getId());
		
	}
	
	
	@Test
	public void searchByNameWithReturnName(){
		testEntityManager.persistFlushFind(category);
		
		Optional<Category> result = categoryRepository.findByName(name);
		Assertions.assertFalse(result.isEmpty());

	    Assertions.assertNotNull(category.getId());
	    Assertions.assertEquals(name, category.getName());
	}

}
