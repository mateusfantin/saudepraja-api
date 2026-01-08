package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.domain.model.entity.user.enumeration.MedicSpecialtyEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "Represents a Scheduling with authenticated user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SchedulingDTO(

        @NotNull(message = "A data de sua preferência deve ser informada")
        LocalDate datScheduling,

        @Size(max = 30, message = "Favor não ultrapassar 30 caracteres na especialidade")
        @NotNull(message = "Favor fornecer a especialidade")
        MedicSpecialtyEnum medicSpecialty,

        @Size(max = 200, message = "Favor não ultrapassar 200 caracteres no campo de observação")
        String obs,

        @NotNull(groups = UnauthenticatedUserGroup.class)
        @CPF
        String cpf,

        @Size(max = 13, message = "Favor não ultrapassar 13 caracteres no campo telefone")
        @NotNull(groups = UnauthenticatedUserGroup.class)
        String telefone

) implements Serializable {

        public interface UnauthenticatedUserGroup {}
}

