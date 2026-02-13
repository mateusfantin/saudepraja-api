package br.com.saudepraja.domain.repository.user;

import br.com.saudepraja.domain.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
