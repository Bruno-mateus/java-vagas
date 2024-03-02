package br.com.brunomateus.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunomateus.gestao_vagas.modules.company.entities.JobEntity;
import br.com.brunomateus.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/job")
public class CreateJobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody JobEntity jobEntity){
        try{
           var result = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
