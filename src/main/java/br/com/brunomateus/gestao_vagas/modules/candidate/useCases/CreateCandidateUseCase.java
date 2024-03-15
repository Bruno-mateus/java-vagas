package br.com.brunomateus.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brunomateus.gestao_vagas.exceptions.UserFoundException;
import br.com.brunomateus.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.controllers.CandidateRepository;


@Service
public class CreateCandidateUseCase {
  
  @Autowired
  private PasswordEncoder passwordEncoder;

@Autowired
  private CandidateRepository candidateRepository;


  public CandidateEntity execute(CandidateEntity candidateEntity){

    this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),candidateEntity.getEmail())
    .ifPresent((user)->{
        throw new UserFoundException();
    });
    var password = this.passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);
    return candidateRepository.save(candidateEntity);
   }

}
