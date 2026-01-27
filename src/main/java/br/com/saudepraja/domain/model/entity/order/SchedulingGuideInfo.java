package br.com.saudepraja.domain.model.entity.order;

import br.com.saudepraja.api.core.storage.StorageProperties;

import java.time.LocalDateTime;

public record SchedulingGuideInfo(String customerName, String customerCpf, String customerAge,
                                  LocalDateTime datScheduling, String address, String city,
                                  String zipCode, LocalDateTime issueDate, String guideNumber) {
}
