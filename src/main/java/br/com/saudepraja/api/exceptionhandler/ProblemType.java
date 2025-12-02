package br.com.saudepraja.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "https://saudepraja.com.br" + path;
        this.title = title;
    }
}
