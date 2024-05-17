package com.ProvaGrupo.SpringEcommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Users {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 16, message = "User name needs to be at least 5 characters long and at most 16 characters long")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    @Pattern(regexp = ".*[a-z].*", message = "The password must contain at least one lowercase letter")
    @Pattern(regexp = ".*[A-Z].*", message = "The password must contain at least one uppercase letter")
    @Pattern(regexp = ".*\\d.*", message = "The password must contain at least one digit")
    @Pattern(regexp = ".*[@#$%^&+=.!].*", message = "The password must contain at least one special character")
    private String password;
    
    @Transient
    @NotBlank(message = "Password Confirmation is Required")
    @Size(min = 8, message = "The confirmation password must be at least 8 characters long")
    @Pattern(regexp = ".*[a-z].*", message = "The password must contain at least one lowercase letter")
    @Pattern(regexp = ".*[A-Z].*", message = "The password must contain at least one uppercase letter")
    @Pattern(regexp = ".*\\d.*", message = "The password must contain at least one digit")
    @Pattern(regexp = ".*[@#$%^&+=.!].*", message = "The password must contain at least one special character")
    private String passwordConfirmation;
    
    private boolean enabled;

    public Users(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public boolean isPasswordConfirmed() {
        return password != null && password.equals(passwordConfirmation);
    }
}