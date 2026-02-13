package br.com.saudepraja.domain.dto.order;

import br.com.saudepraja.domain.model.enumeration.MedicSpecialtyEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "Represents a Scheduling with authenticated user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SchedulingDTO(

        @NotNull(message = "A data de sua preferência deve ser informada")
        LocalDateTime datScheduling,

        @NotNull(message = "Favor fornecer a especialidade")
        MedicSpecialtyEnum medicSpecialty,

        @Size(max = 200, message = "Favor não ultrapassar 200 caracteres no campo de observação")
        String obs,

        @Size(max = 13, message = "Favor não ultrapassar 13 caracteres no campo telefone")
        String telefone

) implements Serializable {
}

