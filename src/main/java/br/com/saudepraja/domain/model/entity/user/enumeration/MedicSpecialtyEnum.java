package br.com.saudepraja.domain.model.entity.user.enumeration;

import lombok.Getter;

@Getter
public enum MedicSpecialtyEnum {

    CLINICA_MEDICA("Clínica Médica"),
    PEDIATRIA("Pediatria"),
    CIRURGIA_GERAL("Cirurgia Geral"),
    GINECOLOGIA_E_OBSTETRICIA("Ginecologia e Obstetrícia"),
    ANESTESIOLOGIA("Anestesiologia "),
    CARDIOLOGIA("Cardiologia"),
    ORTOPEDIA_E_TRAUMATOLOGIA("Ortopedia e Traumatologia"),
    OFTALMOLOGIA("Oftalmologia"),
    ODONTOLOGIA("Odontologia"),
    CIRURGIA_ODONTOLOGICA("Cirurgia Odontológica"),
    NENHUMA_DAS_ALTERNATIVAS("Nenhuma das alternativas");

    private final String descricao;

    MedicSpecialtyEnum(String descricao) {
        this.descricao = descricao;
    }

}
