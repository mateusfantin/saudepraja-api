package br.com.saudepraja.domain.model.repository.user;

import br.com.saudepraja.domain.model.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
