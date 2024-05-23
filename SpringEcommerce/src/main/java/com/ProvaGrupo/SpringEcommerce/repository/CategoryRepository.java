package com.ProvaGrupo.SpringEcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ProvaGrupo.SpringEcommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}