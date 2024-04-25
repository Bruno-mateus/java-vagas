package br.com.brunomateus.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.com.brunomateus.gestao_vagas.modules.candidate.dto.ProfileCandidateDTO;
import br.com.brunomateus.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.FindJobByDescriptionUseCase;
import br.com.brunomateus.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.brunomateus.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {
 
  @Autowired
  CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private FindJobByDescriptionUseCase findJobByDescriptionUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @PostMapping("/create")
  @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "Usuário já existe")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
      try{
        var result = this.createCandidateUseCase.execute(candidateEntity);
        return ResponseEntity.ok().body(result);
      }catch(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }

  }
  @Operation(summary = "Procura um usuário por ID",description ="Essa função encontra um usuário através do id do usuario")
  @ApiResponses(
    {@ApiResponse(responseCode = "200",content = @Content(schema = @Schema(implementation = ProfileCandidateDTO.class))),
    @ApiResponse(responseCode = "400",description = "User not found")}
  )
  @GetMapping("/profile")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> get(HttpServletRequest request){
    var candidate_id = request.getAttribute("candidate_id"); 
   
    try{
        var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidate_id.toString()));
      return ResponseEntity.ok().body(candidate);
    }catch(Exception e){

      e.printStackTrace();
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

     
  @GetMapping("/find-job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada na descrição")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @SecurityRequirement(name="jwt-auth")
  public ResponseEntity<Object> get(@Valid @RequestParam String jobDescription){
      try{
         var result =  this.findJobByDescriptionUseCase.execute(jobDescription);
         return ResponseEntity.ok().body(result);
      }catch(Exception error){
          System.out.println(error.getMessage());
          return ResponseEntity.badRequest().body(error.getMessage());
      }
  }

@PostMapping("/job/apply")
@PreAuthorize("hasRole('CANDIDATE')")
@Operation(summary="Aplica uma vaga a um candidato",description = "Essa função é responsável por aplicar uma vaga a um funcionario")
@ApiResponse(responseCode = "200",content = {
  @Content(schema=@Schema(implementation = ApplyJobEntity.class))
})
@SecurityRequirement(name="jwt-auth")
public ResponseEntity<Object> applyToJob(HttpServletRequest request, @RequestBody UUID jobId){
  var candidateId = request.getAttribute("candidate_id");

  try{
    var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);

    return ResponseEntity.ok().body(result);
  }catch(Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}


}
