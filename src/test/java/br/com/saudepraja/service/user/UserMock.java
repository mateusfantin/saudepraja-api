package br.com.saudepraja.service.user;

import br.com.saudepraja.domain.model.entity.user.Customer;
import br.com.saudepraja.domain.model.entity.user.Customers;
import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.user.enumeration.UserTypeEnum;

import java.time.LocalDate;

public class UserMock {

    private UserMock() {
    }

    public static Users newUser(Long userId, UserTypeEnum userType) {

        return new Users.Builder()
                .withName("Mateus")
                .withEmail("mateus@saudepraja.com.br")
                .withPassword("123456")
                .withUserType(userType)
                .withCustomer(userType.isCustomer() ? newCustomer() : null)
                .build();
    }

    public static Users existentUser(UserTypeEnum userType) {
        return newUser(10L, userType);
    }

    public static Customer newCustomer() {
        return Customer.builder()
                .withTelephone("43999999999")
                .withCpf("12345678910")
                .withBirthday(LocalDate.now().minusYears(30))
                .build();
    }

}
