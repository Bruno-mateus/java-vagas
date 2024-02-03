package br.com.brunomateus.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.brunomateus.gestao_vagas.modules.candidate.CandidateEntity;

import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/candidate")
public class CandidateController { 
    @PostMapping("/")
   public static void create(@RequestBody CandidateEntity candidate){
       System.out.println(candidate);
   }   
    
}
