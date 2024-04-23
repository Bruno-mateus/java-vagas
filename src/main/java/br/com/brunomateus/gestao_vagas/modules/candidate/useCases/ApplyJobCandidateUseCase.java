package br.com.brunomateus.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.brunomateus.gestao_vagas.exceptions.JobNotFoundExeception;
import br.com.brunomateus.gestao_vagas.exceptions.UserNotFoundExeception;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.brunomateus.gestao_vagas.modules.company.repositories.JobRepository;

public class ApplyJobCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JobRepository jobRepository;

    public void execute(UUID candidate_id,UUID job_id){
        //find candidate
        this.candidateRepository.findById(candidate_id)
        .orElseThrow(()->{
            throw new UserNotFoundExeception();
        });

        // find job
        this.jobRepository.findById(job_id).
        orElseThrow(()->{ 
            throw new JobNotFoundExeception();
        });
    }
}
