package br.com.saudepraja.domain.model.util;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
@MappedSuperclass
public class EntityDefaultInsert {

    @Id @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Version
    private Integer version;
}
