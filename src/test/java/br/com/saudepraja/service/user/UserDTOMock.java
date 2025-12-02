package br.com.saudepraja.service.user;

import br.com.saudepraja.domain.model.entity.user.dto.UserDTO;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO.CustomerDTO;

import br.com.saudepraja.domain.model.entity.user.enumeration.UserTypeEnum;

public class UserDTOMock {

    private UserDTOMock() {
    }

    public static UserDTO newUserDTO(Long userId, UserTypeEnum userType) {
        CustomerDTO customerDTO = null;

        if(userType.isCustomer()) {
            customerDTO = newCustomerDTO();
        }

        return UserDTO.builder()
                .withId(userId)
                .withName("Mateus")
                .withEmail("mateus@saudepraja.com.br")
                .withUserType(userType)
                .withPassword("123456")
                .withCustomerDTO(customerDTO)
                .build();
    }

    public static CustomerDTO newCustomerDTO() {
        return UserDTO.customerBuilder()
                .withTelephone("43999999999")
                .withCpf("12345678910")
                .withBirthday("31/12/1990")
                .build();
    }
}
