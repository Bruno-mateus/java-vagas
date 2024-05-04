package br.com.brunomateus.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    @Schema(example = "Vaga Backend Java spring boot")
    private String description;
    @Schema(example="Pleno")
    private String level;
    @Schema(example = "Gympass, Unimed")
    private String benefits;
}
