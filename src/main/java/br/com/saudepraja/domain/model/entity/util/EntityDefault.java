package br.com.saudepraja.domain.model.entity.util;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
@MappedSuperclass
public class EntityDefault {

    @Id @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Column(name = "upload_date")
    private LocalDateTime update;

    @Version
    private Integer version;
}
