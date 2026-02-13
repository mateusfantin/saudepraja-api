package br.com.saudepraja.domain.model.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "template")
public class Template {

    public static final String SERVICE_GUIDE = "SERVICE_GUIDE";

    @Id
    @Column(name = "email_template_id", length = 50)
    private String id;

    @Column(name = "txt_template", length = 4000)
    private String txtTemplate;

}

