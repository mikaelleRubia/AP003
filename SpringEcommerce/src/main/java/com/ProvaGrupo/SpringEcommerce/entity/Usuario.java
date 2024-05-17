package com.ProvaGrupo.SpringEcommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Email obrigatório")
	@Email(message = "Email inválido")
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	@NotNull(message = "Password obrigatório")
	@Size(min = 8, message = "Password deve ter pelo menos 8 caracteres")
	private String password;
	
}
