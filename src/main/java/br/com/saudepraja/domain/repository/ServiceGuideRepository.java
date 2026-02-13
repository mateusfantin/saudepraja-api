package br.com.saudepraja.domain.repository;

import br.com.saudepraja.domain.model.order.ServiceGuide;
import org.springframework.data.repository.CrudRepository;

public interface ServiceGuideRepository extends CrudRepository<ServiceGuide, String> {
}
