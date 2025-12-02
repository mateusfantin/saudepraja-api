package br.com.saudepraja.domain.model.repository.user;

import br.com.saudepraja.domain.model.entity.user.Medic;
import org.springframework.data.repository.CrudRepository;

public interface MedicRepository extends CrudRepository<Medic, Long> {

}
