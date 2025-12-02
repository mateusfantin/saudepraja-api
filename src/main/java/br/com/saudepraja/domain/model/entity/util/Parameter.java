package br.com.saudepraja.domain.model.entity.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

@Getter
@Setter
@Entity
@Table(name="parameter")
public class Parameter {

    @Id @Column(length = 100)
    String id;

    @Column(name = "text", length = 200)
    String text;

}
