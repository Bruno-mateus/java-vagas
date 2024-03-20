package br.com.brunomateus.gestao_vagas.modules.candidate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brunomateus.gestao_vagas.modules.candidate.CandidateEntity;



public interface CandidateRepository extends JpaRepository<CandidateEntity,UUID>{
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);
    @SuppressWarnings("null")
    Optional<CandidateEntity> findById(UUID id);
}
