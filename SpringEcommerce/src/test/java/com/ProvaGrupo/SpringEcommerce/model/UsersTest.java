package com.ProvaGrupo.SpringEcommerce.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsersTest {

	private Validator validator;
	private Faker faker;

	private Users user;

	@BeforeEach
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		faker = new Faker();

		String password = faker.internet().password(8, 16, true, true, true);

		user = Users.builder().id(1L).email(faker.internet().emailAddress())
				.username(faker.regexify("[a-zA-Z0-9]{5,16}")).password(password).passwordConfirmation(password)
				.enabled(true).build();
	}

	@Test
	public void testUser_CreationWithNotViolations() {
		Set<ConstraintViolation<Users>> violations = validator.validate(user);

		assertTrue(violations.isEmpty());
		log.info("No validation violations found for user creation.");
	}

	@Test
	public void testEmail_CreationWithNotBlankViolations_ReturnEmailIsRequired() {
		user.setEmail("");
		Set<ConstraintViolation<Users>> violationsEmpty = validator.validate(user);
		assertFalse(violationsEmpty.isEmpty());
		assertTrue(violationsEmpty.stream().anyMatch(violation -> violation.getMessage().equals("Email is required")));

		user.setEmail(null);
		Set<ConstraintViolation<Users>> violationsNull = validator.validate(user);
		assertFalse(violationsNull.isEmpty());
		assertTrue(violationsNull.stream().anyMatch(violation -> violation.getMessage().equals("Email is required")));

		log.info("Email validation tests completed.");
	}

	@Test
	public void testUsername_CreationWithNotBlankViolations_ReturnUserNameIsRequired() {
		user.setUsername("");

		Set<ConstraintViolation<Users>> violationsEmpty = validator.validate(user);
		assertFalse(violationsEmpty.isEmpty());
		assertTrue(violationsEmpty.stream().anyMatch(v -> v.getMessage().contains("Username is required")));

		user.setUsername(null);

		Set<ConstraintViolation<Users>> violationsNull = validator.validate(user);
		assertFalse(violationsNull.isEmpty());
		assertTrue(violationsNull.stream().anyMatch(v -> v.getMessage().contains("Username is required")));

		log.info("Username validation tests completed.");
	}

	@Test
	public void testUsername_CreationWithSizeViolations_ReturnSizeViolation() {
		user.setUsername("Mi12");

		Set<ConstraintViolation<Users>> violationsMin = validator.validate(user);
		assertFalse(violationsMin.isEmpty());
		assertTrue(violationsMin.stream().anyMatch(v -> v.getMessage()
				.contains("User name needs to be at least 5 characters long and at most 16 characters long")));

		user.setUsername("TesteMaxName12345");

		Set<ConstraintViolation<Users>> violationsMax = validator.validate(user);
		assertFalse(violationsMax.isEmpty());
		assertTrue(violationsMax.stream().anyMatch(v -> v.getMessage()
				.contains("User name needs to be at least 5 characters long and at most 16 characters long")));

		log.info("Username size validation tests completed.");
	}

	@Test
	public void testPassword_CreationWithNotBlankViolations_ReturnPasswordIsRequired() {
		user.setPassword("");

		Set<ConstraintViolation<Users>> violationsEmpty = validator.validate(user);
		assertFalse(violationsEmpty.isEmpty());
		assertTrue(violationsEmpty.stream().anyMatch(v -> v.getMessage().contains("Password is required")));

		user.setPassword(null);

		Set<ConstraintViolation<Users>> violationsNull = validator.validate(user);
		assertFalse(violationsNull.isEmpty());
		assertTrue(violationsNull.stream().anyMatch(v -> v.getMessage().contains("Password is required")));

		log.info("Password validation tests completed.");
	}

	@Test
	public void testPassword_CreationWithSizeViolations_ReturnPasswordSizeViolation() {
		user.setPassword("1@Teste");

		Set<ConstraintViolation<Users>> violationsMin = validator.validate(user);
		assertFalse(violationsMin.isEmpty());
		assertTrue(violationsMin.stream()
				.anyMatch(v -> v.getMessage().contains("The password must be at least 8 characters long")));

		log.info("Password size validation tests completed.");
	}

	@Test
	public void testPassword_CreationWithContainsViolations_ReturnPasswordContainsViolations() {
		user.setPassword("TESTE@123");

		Set<ConstraintViolation<Users>> violationsLowercase = validator.validate(user);
		assertFalse(violationsLowercase.isEmpty());
		assertTrue(violationsLowercase.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one lowercase letter")));

		user.setPassword("teste@123");

		Set<ConstraintViolation<Users>> violationsUppercase = validator.validate(user);
		assertFalse(violationsUppercase.isEmpty());
		assertTrue(violationsUppercase.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one uppercase letter")));

		user.setPassword("Teste@Abc");

		Set<ConstraintViolation<Users>> violationsDigit = validator.validate(user);
		assertFalse(violationsDigit.isEmpty());
		assertTrue(violationsDigit.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one digit")));

		user.setPassword("Teste1234");

		Set<ConstraintViolation<Users>> violationsSpecialCaractere = validator.validate(user);
		assertFalse(violationsSpecialCaractere.isEmpty());
		assertTrue(violationsSpecialCaractere.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one special character")));

		log.info("Password contains validation tests completed.");
	}

	@Test
	public void testPasswordConfirmation_CreationWithNotBlankViolations_ReturnPasswordIsRequired() {
		user.setPasswordConfirmation("");

		Set<ConstraintViolation<Users>> violationsEmpty = validator.validate(user);
		assertFalse(violationsEmpty.isEmpty());
		assertTrue(
				violationsEmpty.stream().anyMatch(v -> v.getMessage().contains("Password Confirmation is Required")));

		user.setPasswordConfirmation(null);

		Set<ConstraintViolation<Users>> violationsNull = validator.validate(user);
		assertFalse(violationsNull.isEmpty());
		assertTrue(violationsNull.stream().anyMatch(v -> v.getMessage().contains("Password Confirmation is Required")));

		log.info("Password confirmation validation tests completed.");
	}

	@Test
	public void testPasswordConfirmation_CreationWithSizeViolations_ReturnPasswordSizeViolation() {
		user.setPasswordConfirmation("1@Teste");

		Set<ConstraintViolation<Users>> violationsMin = validator.validate(user);
		assertFalse(violationsMin.isEmpty());
		assertTrue(violationsMin.stream().anyMatch(
				v -> v.getMessage().contains("The confirmation password must be at least 8 characters long")));

		log.info("Password confirmation size validation tests completed.");
	}

	@Test
	public void testPasswordConfirmation_CreationWithContainsViolations_ReturnPasswordContainsViolations() {
		user.setPasswordConfirmation("TESTE@123");

		Set<ConstraintViolation<Users>> violationsLowercase = validator.validate(user);

		assertFalse(violationsLowercase.isEmpty());
		assertTrue(violationsLowercase.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one lowercase letter")));

		user.setPasswordConfirmation("teste@123");

		Set<ConstraintViolation<Users>> violationsUppercase = validator.validate(user);
		assertFalse(violationsUppercase.isEmpty());
		assertTrue(violationsUppercase.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one uppercase letter")));

		user.setPasswordConfirmation("Teste@Abc");

		Set<ConstraintViolation<Users>> violationsDigit = validator.validate(user);
		assertFalse(violationsDigit.isEmpty());
		assertTrue(violationsDigit.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one digit")));

		user.setPasswordConfirmation("Teste1234");

		Set<ConstraintViolation<Users>> violationsSpecialCaractere = validator.validate(user);
		assertFalse(violationsSpecialCaractere.isEmpty());
		assertTrue(violationsSpecialCaractere.stream()
				.anyMatch(v -> v.getMessage().contains("The password must contain at least one special character")));

		log.info("Password confirmation contains validation tests completed.");
	}

	@Test
	public void testPasswordAndConfirmationMatch() {
		boolean confirmation = user.isPasswordConfirmed();
		assertTrue(confirmation);
		log.info("Password and confirmation match test completed.");
	}

	@Test
	public void testPasswordAndConfirmationDoNotMatch() {
		user.setPasswordConfirmation("Teste@123False");
		boolean confirmation = user.isPasswordConfirmed();
		assertFalse(confirmation);
		log.info("Password and confirmation do not match test completed.");
	}
}
