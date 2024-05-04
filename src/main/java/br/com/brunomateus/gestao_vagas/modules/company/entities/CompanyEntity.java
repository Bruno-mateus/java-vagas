package br.com.brunomateus.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)    
    private UUID id;
    private String name;
    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    private String username;
    @Email(message = "O campo [e-mail] deve conter um e-mail valido")
    private String email;

    @Length(min = 8, max = 100, message="A senha dave ter entre 8 a 100 caracteres")   
    private String password;
    private String website;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
}