package br.com.brunomateus.gestao_vagas.modules.candidate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.brunomateus.gestao_vagas.exceptions.JobNotFoundExeception;
import br.com.brunomateus.gestao_vagas.exceptions.UserNotFoundExeception;
import br.com.brunomateus.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.brunomateus.gestao_vagas.modules.company.entities.JobEntity;
import br.com.brunomateus.gestao_vagas.modules.company.repositories.JobRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should be not able apply to job when the candidate is not found.")
    public void should_be_not_able_apply_to_job_when_the_candidate_is_not_found(){
        try{
            this.applyJobCandidateUseCase.execute(null, null);
        }catch(Exception e){
            assertThat(e).isInstanceOf(UserNotFoundExeception.class);
        }
    }

    @Test
    @DisplayName("Should be not able apply to job when the job is not found.")
    public void should_be_not_able_apply_to_job_when_the_job_is_not_found(){
        //simulate a id for candidate
        UUID id_candidate = UUID.randomUUID();
        //mock candidate 
        var candidate = new CandidateEntity();
        candidate.setId(id_candidate);
        //then returned a candidate when call this.candidateRepository.findById(id_candidate);
        when(this.candidateRepository.findById(id_candidate)).thenReturn(Optional.of(candidate));
        try{
            this.applyJobCandidateUseCase.execute(id_candidate, null);
        }catch(Exception e){
            assertThat(e).isInstanceOf(JobNotFoundExeception.class);
        }
    }
    @Test
    @DisplayName("Should be create a new apply job")
    public void should_be_create_a_new_apply_job(){
        //teste do useCase
        var candidate_id= UUID.randomUUID();
        var job_id= UUID.randomUUID(); 
        

        when(this.jobRepository.findById(job_id)).thenReturn(Optional.of(new JobEntity()));
        when(this.candidateRepository.findById(candidate_id)).thenReturn(Optional.of(new CandidateEntity()));
        
        var applyJob = ApplyJobEntity
                        .builder()
                        .jobId(job_id)
                        .candidateId(candidate_id)         
                        .build();
        //FOI PRECISO CRIAR NOVAMENTE AQUI PQ É ASSIM QUE O REPOSITORY AGE, SÓ FAZ O ID UUID ANTES DE GRAVAR NO BANCO DE
        var applyJobCreated = ApplyJobEntity
                            .builder()
                            .id(UUID.randomUUID())
                            .build();


        when(this.applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = this.applyJobCandidateUseCase.execute(candidate_id,job_id);
        
        assertThat(result).hasFieldOrProperty("id");

    }

}
