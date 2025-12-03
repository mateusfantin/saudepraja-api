package br.com.saudepraja.domain.model.repository.user;

import br.com.saudepraja.domain.model.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String username);
}
