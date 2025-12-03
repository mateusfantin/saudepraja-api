package br.com.saudepraja.domain.model.entity.user.dto;

import br.com.saudepraja.domain.model.entity.user.enumeration.MedicSpecialtyEnum;
import br.com.saudepraja.domain.model.entity.user.enumeration.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;

    @NotNull(groups = DefaultUserGroup.class, message = "Favor informar um nome")
    @Size(max = 100, message = "No campo NOME favor não ultrapassar 100 caracteres")
    private String name;

    @NotNull(groups = DefaultUserGroup.class, message = "Favor informar o email")
    @Size(max = 100, message = "No campo EMAIL, favor não ultrapasse 100 caracteres")
    private String email;

    @NotNull(groups = DefaultUserGroup.class, message = "Favor escolher uma senha")
    @Size(max = 50, message = "No campo SENHA, favor não ultrapassar 50 caracteres")
    private String password;

    @NotNull(groups = DefaultUserGroup.class, message = "Favor informar o tipo de usuário")
    private UserTypeEnum userType;

    @Valid
    @NotNull(groups = DefaultCustomerGroup.class)
    private CustomerDTO customerDTO;

    @Valid
    @NotNull(groups = DefaultMedicGroup.class)
    private MedicDTO medicDTO;

    public UserDTO(Long id, String name, String email, String password, UserTypeEnum userType, CustomerDTO customerDTO) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.customerDTO = customerDTO;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static CustomerBuilder customerBuilder() {
        return new CustomerBuilder();
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class MedicDTO implements Serializable {

        @NotNull(message = "Favor informar o CRM")
        @Size(max = 10, message = "O campo não deve ultrapassar 10 caracteres")
        private Integer crm;

        private MedicSpecialtyEnum medicSpecialty;

        private String subspecialty;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class CustomerDTO implements Serializable {

        @NotNull(message = "Favor informar um telefone")
        @Size(max = 20, message = "No campo TELEFONE, favor não ultrapassar 20 caracteres")
        private String telephone;

        @NotNull(message = "Favor informar o cpf")
        @Size(max = 14, message = "No campo CPF, favor não ultrapassar 14 caracteres")
        private String cpf;

        @NotNull(message = "Favor informar a data de nascimento")
        @Size(max = 10, message = "No campo DATA NASCIMENTO, favor não ultrapassar 10 caracteres")
        private String birthday;

    }

    @Getter
    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private UserTypeEnum userType;
        private CustomerDTO customerDTO;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withUserType(UserTypeEnum userType) {
            this.userType = userType;
            return this;
        }

        public Builder withCustomerDTO(CustomerDTO customerDTO) {
            this.customerDTO = customerDTO;
            return this;
        }

        public UserDTO buildWithoutCustomer() {
            return new UserDTO(this.getId(),
                    this.getName(),
                    this.getEmail(),
                    this.getPassword(),
                    this.getUserType(),
                    null);
        }

        public UserDTO build() {
            return new UserDTO(this.getId(),
                    this.getName(),
                    this.getEmail(),
                    this.getPassword(),
                    this.getUserType(),
                    this.getCustomerDTO());
        }

    }

    @Getter
    public static class CustomerBuilder {
        private String telephone;
        private String cpf;
        private String birthday;

        public CustomerBuilder withTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public CustomerBuilder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public CustomerBuilder withBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this.getTelephone(),
                    this.getCpf(),
                    this.getBirthday());
        }
    }

    public interface DefaultUserGroup {}
    public interface DefaultCustomerGroup {}
    public interface DefaultMedicGroup{}
}
