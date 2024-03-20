package br.com.brunomateus.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.brunomateus.gestao_vagas.modules.candidate.dto.ProfileCandidateDTO;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

 public ProfileCandidateDTO execute(UUID company_id){
    
    var candidate = this.candidateRepository.findById(company_id)
                    .orElseThrow(()->{
                        throw new UsernameNotFoundException("User not found");
                    });

    var candidateDTO = ProfileCandidateDTO.builder()
                        .description(candidate.getDescription())
                        .email(candidate.getEmail())
                        .name(candidate.getName())
                        .username(candidate.getUsername())
                        .id(candidate.getId())
                        .build();
    return candidateDTO;
 }
}
