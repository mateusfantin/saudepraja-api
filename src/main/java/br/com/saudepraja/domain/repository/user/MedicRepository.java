package br.com.saudepraja.domain.repository.user;

import br.com.saudepraja.domain.model.user.Medic;
import org.springframework.data.repository.CrudRepository;

public interface MedicRepository extends CrudRepository<Medic, Long> {

}
