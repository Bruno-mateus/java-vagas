package br.com.brunomateus.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brunomateus.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.brunomateus.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;

@RestController
@RequestMapping("/company")
class CreateCompanyController{
    
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody CompanyEntity companyEntity){
        try{
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}