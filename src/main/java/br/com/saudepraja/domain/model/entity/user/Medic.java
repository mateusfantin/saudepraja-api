package br.com.saudepraja.domain.model.entity.user;

import br.com.saudepraja.domain.model.entity.user.enumeration.MedicSpecialtyEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table
@Entity(name= "medic")
public class Medic {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Users users;

    @Size(max = 10)
    @Column(name = "num_crm", nullable = false)
    private Integer crm;

    @Enumerated(EnumType.STRING)
    private MedicSpecialtyEnum medicSpecialty;

    @Size(max = 100)
    @Column(name = "dsc_subspecialty")
    private String subspecialty;

}
