package br.com.saudepraja.domain.model.enumeration;

public enum SchedulingStatusEnum {

    WAITING,
    SCHEDULED,
    COMPLETED;

    public boolean isScheduled() {
        return this.equals(SCHEDULED);
    }

}
