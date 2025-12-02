package br.com.saudepraja.domain.service.user;

import br.com.saudepraja.domain.model.entity.user.Customer;
import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO.CustomerDTO;
import br.com.saudepraja.domain.model.repository.user.CustomerRepository;
import br.com.saudepraja.domain.service.user.utils.SaudePrajaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

@Service
class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    protected void save(Users users, @Validated(UserDTO.DefaultCustomerGroup.class) CustomerDTO customerDTO) {
        Objects.requireNonNull(users, "Object type User.class don't be null");
        Objects.requireNonNull(customerDTO, "Object type CustomerDTO.class don't be null");
        LocalDate birthday = SaudePrajaUtils.stringToLocalDate(customerDTO.getBirthday(), SaudePrajaUtils.dayMonthYearBRType);
        Customer customer = new Customer(users, customerDTO.getTelephone(), customerDTO.getCpf(), birthday);
        customerRepository.save(customer);
    }

}
