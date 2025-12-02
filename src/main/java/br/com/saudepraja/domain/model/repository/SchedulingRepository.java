package br.com.saudepraja.domain.model.repository;

import br.com.saudepraja.domain.model.entity.order.Scheduling;
import org.springframework.data.repository.CrudRepository;

public interface SchedulingRepository extends CrudRepository<Scheduling, Long> {
}
