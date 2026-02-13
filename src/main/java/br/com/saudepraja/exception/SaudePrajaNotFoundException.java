package br.com.saudepraja.exception;

public class SaudePrajaNotFoundException extends RuntimeException {
    public SaudePrajaNotFoundException(String message) {
        super(message);
    }
}
