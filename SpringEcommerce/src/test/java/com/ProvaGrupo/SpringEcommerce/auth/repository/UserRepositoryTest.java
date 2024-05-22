package com.ProvaGrupo.SpringEcommerce.auth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;

import com.ProvaGrupo.SpringEcommerce.auth.model.User;
import com.github.javafaker.Faker;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    
    private Faker faker = new Faker();

    private User user;

    @BeforeEach
    public void setUp() {
        entityManager.clear();

        user = User.builder()
            .login(faker.lorem().characters(5, 15))
            .email(faker.internet().emailAddress())
            .password(faker.lorem().characters(10, 15) + "A1#")
            .build();
    }

    @Test
    public void whenFindByLogin_thenReturnUser() {
        User savedUser = entityManager.persistAndFlush(user);

        UserDetails foundUser = userRepository.findByLogin(savedUser.getLogin());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(savedUser.getLogin());
    }

    @Test
    public void whenNotFindByLogin_thenReturnNull() {
        UserDetails foundUser = userRepository.findByLogin(faker.name().username());

        assertThat(foundUser).isNull();
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User savedUser = entityManager.persistAndFlush(user);

        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void whenNotFindByEmail_thenReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail(faker.internet().emailAddress());

        assertThat(foundUser.isEmpty()).isTrue();
    }

    @Test
    public void whenExistsByLogin_thenReturnTrue() {
        User savedUser = entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByLogin(savedUser.getLogin());

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByLogin_thenReturnFalse() {
        boolean exists = userRepository.existsByLogin(faker.name().username());

        assertThat(exists).isFalse();
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue() {
        User savedUser = entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail(savedUser.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByEmail_thenReturnFalse() {
        boolean exists = userRepository.existsByEmail(faker.internet().emailAddress());

        assertThat(exists).isFalse();
    }
}