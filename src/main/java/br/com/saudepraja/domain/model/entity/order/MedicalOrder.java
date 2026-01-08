package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.domain.model.entity.util.EntityDefaultInsert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "medical_order")
public class MedicalOrder extends EntityDefaultInsert {

    @Column(name = "doctors_name", length = 100)
    private String doctorsName;

}


