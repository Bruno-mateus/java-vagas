package br.com.brunomateus.gestao_vagas.modules.candidate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.brunomateus.gestao_vagas.exceptions.JobNotFoundExeception;
import br.com.brunomateus.gestao_vagas.exceptions.UserNotFoundExeception;
import br.com.brunomateus.gestao_vagas.modules.candidate.repository.CandidateRepository;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
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
        //then returned a candidate when call this.candidateRepository.findById(id_candidate)).thenReturn(Optional.of(candidate));
        when(this.candidateRepository.findById(id_candidate)).thenReturn(Optional.of(candidate));
        try{
            this.applyJobCandidateUseCase.execute(id_candidate, null);
        }catch(Exception e){
            assertThat(e).isInstanceOf(JobNotFoundExeception.class);
        }
    }

}
