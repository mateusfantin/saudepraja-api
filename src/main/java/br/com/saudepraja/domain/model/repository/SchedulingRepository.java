package br.com.saudepraja.domain.model.repository;

import br.com.saudepraja.domain.model.entity.order.Scheduling;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SchedulingRepository extends CrudRepository<Scheduling, Long> {
    Optional<Scheduling>  findById_AndUserId(Long schedulingId, Long userId);
}
