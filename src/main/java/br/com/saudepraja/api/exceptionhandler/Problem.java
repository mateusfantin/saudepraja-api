package br.com.saudepraja.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Problem {

    private Integer status;
    private String type;
    private String title; // generic description
    private String detail; // detailed description
    private LocalDateTime dataHora;

}
