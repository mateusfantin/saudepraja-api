package br.com.saudepraja.domain.model.entity.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "storable")
public class Storable extends EntityDefault {

    @Column(name = "name", length = 100)
    private String name;

    @Transient
    private InputStream inputStream;
}
