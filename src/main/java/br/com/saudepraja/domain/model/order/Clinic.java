package br.com.saudepraja.domain.model.order;

import br.com.saudepraja.domain.model.util.EntityDefaultInsert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "medical_order")
public class Clinic extends EntityDefaultInsert {

    @Column(name = "adrress")
    private String adrress;

    @Column(name = "zip_code", length = 8)
    private String zipCode;

    @Column(name = "city", length = 30)
    private String city;

    public String getZipCode() {
        StringBuilder sb = new StringBuilder(this.zipCode);
        sb.insert(4, "-");
        return sb.toString();
    }

}
