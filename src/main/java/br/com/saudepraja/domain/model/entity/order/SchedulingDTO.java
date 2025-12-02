package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.domain.model.entity.user.enumeration.MedicSpecialtyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "Represents a Scheduling")
public record SchedulingDTO(

        @NotNull(message = "A data de sua preferência deve ser informada")
        LocalDate datScheduling,

        @Size(max = 30)
        @NotNull(message = "Favor fornecer a especialidade")
        MedicSpecialtyEnum medicSpecialty,

        @Size(max = 200, message = "Favor não ultrapassar 200 caracteres")
        String obs) implements Serializable {
}
