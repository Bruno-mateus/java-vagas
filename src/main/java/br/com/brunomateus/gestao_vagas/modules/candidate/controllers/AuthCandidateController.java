package br.com.brunomateus.gestao_vagas.modules.candidate.controllers;
import br.com.brunomateus.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth") 
    ResponseEntity<Object> auth(@RequestBody AuthCandidateDTO authCandidateDTO){
        try{
            
            var token = this.authCandidateUseCase.execute(authCandidateDTO);
           
            return ResponseEntity.ok().body(token);

        }catch(Exception e){

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
