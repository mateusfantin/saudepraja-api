package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.domain.model.entity.user.enumeration.MedicSpecialtyEnum;
import br.com.saudepraja.domain.model.entity.util.EntityDefault;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Entity
@Table(name = "scheduling")
public class Scheduling extends EntityDefault {

    @Column(name = "dat_scheduling")
    private LocalDate datScheduling;

    @Enumerated(EnumType.STRING)
    @Column(name = "medic_specialty", length = 30)
    private MedicSpecialtyEnum medicSpecialty;

    @Column(name = "dsc_obs", length = 200)
    private String obs;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "telephone", length = 13)
    private String telephone;

    @OneToMany(mappedBy = "id")
    private List<Upload> upload;

}
