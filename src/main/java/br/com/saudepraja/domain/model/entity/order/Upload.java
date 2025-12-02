package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.domain.model.entity.util.EntityDefaultInsert;
import jakarta.persistence.*;

@Entity
@Table(name= "upload")
public class Upload extends EntityDefaultInsert {

    @Column(name = "file_name", length = 100)
    private String fileName;

    @Column(name = "path", length = 1000)
    private String path;

}
