package br.com.saudepraja.domain.model.order;

import br.com.saudepraja.domain.model.enumeration.MedicSpecialtyEnum;
import br.com.saudepraja.domain.model.enumeration.SchedulingStatusEnum;
import br.com.saudepraja.domain.model.util.EntityDefault;
import br.com.saudepraja.domain.model.util.Storable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Entity
@Table(name = "scheduling")
public class Scheduling extends EntityDefault {

    @Column(name = "dat_scheduling")
    private LocalDateTime datScheduling;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SchedulingStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "medic_specialty", length = 30)
    private MedicSpecialtyEnum medicSpecialty;

    @Column(name = "dsc_obs", length = 200)
    private String obs;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "telephone", length = 13)
    private String telephone;

    @OneToMany(mappedBy = "id")
    private List<Storable> storables;

    @OneToOne
    @JoinColumn(name = "service_guide_id")
    private ServiceGuide serviceGuide;

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

}
