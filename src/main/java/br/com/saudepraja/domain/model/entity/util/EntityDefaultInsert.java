package br.com.saudepraja.domain.model.entity.util;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public class EntityDefaultInsert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Version
    private Integer version;
}
