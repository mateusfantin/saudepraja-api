package br.com.saudepraja.domain.model.repository.user;

import br.com.saudepraja.domain.model.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
