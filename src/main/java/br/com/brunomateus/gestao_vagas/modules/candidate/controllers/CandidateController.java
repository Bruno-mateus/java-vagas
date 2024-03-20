package br.com.brunomateus.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.brunomateus.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
 
  @Autowired
  CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  ProfileCandidateUseCase profileCandidateUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
      try{
        var result = this.createCandidateUseCase.execute(candidateEntity);
        return ResponseEntity.ok().body(result);
      }catch(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }

  }

  @GetMapping("/profile")
  public ResponseEntity<Object> get(HttpServletRequest request){
    var candidate_id = request.getAttribute("company_id"); //ainda vamos setar
    
    try{
        //tenta buscar o candidato atraves do id
        var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidate_id.toString()));
        //se sucesso o retorna
      return ResponseEntity.ok().body(candidate);
    }catch(Exception e){

      e.printStackTrace();
      //se não encontrar retorna um erro
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
