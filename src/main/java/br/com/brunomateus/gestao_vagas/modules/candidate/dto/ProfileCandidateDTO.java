    package br.com.brunomateus.gestao_vagas.modules.candidate.dto;

    import java.util.UUID;

    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProfileCandidateDTO {
        private UUID   id;
        @Schema(example = "Maria Cristina")
        private String name;
        @Schema(example = "maria_123")
        private String username;
        @Schema(example = "maria@hotmail.com")
        private String email;
        @Schema(example = "Desenvolvedora Java")
        private String description;
    }
