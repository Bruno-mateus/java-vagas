package br.com.brunomateus.gestao_vagas.modules.candidate.useCases;

import br.com.brunomateus.gestao_vagas.modules.company.entities.JobEntity;
import br.com.brunomateus.gestao_vagas.modules.company.repositories.JobRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FindJobByDescriptionUseCase {
   
    @Autowired
    private JobRepository jobRepository;
 
    public List<JobEntity> execute(String description){
       return this.jobRepository.findByDescriptionContainingIgnoreCase(description);
    
   }
}
