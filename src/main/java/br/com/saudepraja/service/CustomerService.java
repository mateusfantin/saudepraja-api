package br.com.saudepraja.service;

import br.com.saudepraja.domain.model.user.Customer;
import br.com.saudepraja.domain.model.user.Users;
import br.com.saudepraja.domain.dto.user.UserDTO;
import br.com.saudepraja.domain.dto.user.UserDTO.CustomerDTO;
import br.com.saudepraja.domain.repository.user.CustomerRepository;
import br.com.saudepraja.domain.service.SaudePrajaUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    protected void save(Users users, @Validated(UserDTO.DefaultCustomerGroup.class) CustomerDTO customerDTO) {
        Objects.requireNonNull(users, "Object type User.class don't be null");
        Objects.requireNonNull(customerDTO, "Object type CustomerDTO.class don't be null");
        LocalDate birthday = SaudePrajaUtils.stringToLocalDate(customerDTO.getBirthday(), SaudePrajaUtils.dayMonthYearBRType);
        Customer customer = new Customer(users, customerDTO.getTelephone(), customerDTO.getCpf(), birthday);
        customerRepository.save(customer);
    }

}
