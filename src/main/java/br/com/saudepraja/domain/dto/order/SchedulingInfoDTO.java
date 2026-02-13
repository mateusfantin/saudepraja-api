package br.com.saudepraja.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Schema(description = "Represents a Scheduling with complete informations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SchedulingInfoDTO (

        @NotNull(message = "A data de sua preferência deve ser informada")
        LocalDateTime datScheduling,

        @NotNull(message = "O procedimento médico deve ser informado")
        String medicalProcedure,

        @NotNull(message = "O id do cliente deve ser informado")
        Long customerId,

        @NotNull(message = "O id da clínica deve ser informado")
        Long clinicId
){ }


