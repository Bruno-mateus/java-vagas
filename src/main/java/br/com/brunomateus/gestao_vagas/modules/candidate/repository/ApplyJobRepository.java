package br.com.brunomateus.gestao_vagas.modules.candidate.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.brunomateus.gestao_vagas.modules.candidate.entities.ApplyJobEntity;


public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity,UUID> {
    
     Optional<ApplyJobEntity> findById(UUID id);
     
 }
