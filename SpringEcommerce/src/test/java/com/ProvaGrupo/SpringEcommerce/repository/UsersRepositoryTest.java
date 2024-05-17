package com.ProvaGrupo.SpringEcommerce.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ProvaGrupo.SpringEcommerce.model.Users;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Slf4j
public class UsersRepositoryTest {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private Users user;
	private Users user2;

	private Faker faker;

	@BeforeEach
	void setUp() {
        log.info("Setting up test data");

		faker = new Faker();

		String password = faker.internet().password(8, 16, true, true, true);
		String password2 = faker.internet().password(8, 16, true, true, true);

		user = Users.builder().email(faker.internet().emailAddress()).username(faker.regexify("[a-zA-Z0-9]{5,16}"))
				.password(password).passwordConfirmation(password).enabled(true).build();

		user2 = Users.builder().email(faker.internet().emailAddress()).username(faker.regexify("[a-zA-Z0-9]{5,16}"))
				.password(password2).passwordConfirmation(password2).enabled(true).build();
	}

	// Some tests may fail because faker.internet().password() generates some
	// passwords that do not contain the required data for validation. If this
	// happens, rerun the tests.

	@Test
	void saveUser_ShouldInsertAndReturnTheUser() {
        log.info("Testing saveUser: START");

		Users savedUser = usersRepository.save(user);

		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
		assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
		assertThat(savedUser.isEnabled()).isEqualTo(user.isEnabled());

        log.info("Testing saveUser: COMPLETED");
	}

	@Test
	void findByUsername_ReturnUserIfUsernameExistsInDatabase() {
        log.info("Testing findByUsername: START");

		Users savedUser = testEntityManager.persistFlushFind(user);

		Optional<Users> foundUser = usersRepository.findByUsername(savedUser.getUsername());

		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());

        log.info("Testing findByUsername: COMPLETED");
	}

	@Test
	void findByUsername_ReturnEmptyOptionalIfUsernameDoesNotExist() {
        log.info("Testing findByUsername with non-existent username: START");

		Optional<Users> foundUser = usersRepository.findByUsername(faker.name().username());

		assertThat(foundUser).isEmpty();

        log.info("Testing findByUsername with non-existent username: COMPLETED");
	}

	@Test
	void isPasswordConfirmed_ReturnTrueIfPasswordMatchesConfirmation() {
        log.info("Testing isPasswordConfirmed with matching passwords: START");

		Users userWithMatchingPasswords = Users.builder().email(faker.internet().emailAddress())
				.username(faker.regexify("[a-zA-Z0-9]{5,16}")).password("Password123!")
				.passwordConfirmation("Password123!").enabled(true).build();

		Users savedUser = testEntityManager.persistAndFlush(userWithMatchingPasswords);

		assertTrue(savedUser.isPasswordConfirmed());

        log.info("Testing isPasswordConfirmed with matching passwords: COMPLETED");
	}

	@Test
	void isPasswordConfirmed_ReturnFalseIfPasswordDoesNotMatchConfirmation() {
        log.info("Testing isPasswordConfirmed with mismatching passwords: START");

		Users userWithMismatchingPasswords = Users.builder().email(faker.internet().emailAddress())
				.username(faker.regexify("[a-zA-Z0-9]{5,16}")).password("Password123!")
				.passwordConfirmation("Mismatched123!").enabled(true).build();

		Users savedUser = testEntityManager.persistAndFlush(userWithMismatchingPasswords);

		assertFalse(savedUser.isPasswordConfirmed());

        log.info("Testing isPasswordConfirmed with mismatching passwords: COMPLETED");
	}

	@Test
	void findAll_ReturnAllUsers() {
        log.info("Testing findAll: START");

		testEntityManager.persistAndFlush(user);
		testEntityManager.persistAndFlush(user2);

		List<Users> allUsers = usersRepository.findAll();

		assertThat(allUsers).isNotEmpty();
		assertThat(allUsers).contains(user, user2);

        log.info("Testing findAll: COMPLETED");
	}

}
