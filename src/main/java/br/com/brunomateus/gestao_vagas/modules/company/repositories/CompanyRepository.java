package br.com.brunomateus.gestao_vagas.modules.company.repositories;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brunomateus.gestao_vagas.modules.company.entities.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity,UUID> {
    Optional<CompanyEntity> findCompanyByUsernameOrEmail(String username, String email);
    Optional<CompanyEntity> findCompanyByUsername(String username);
}
