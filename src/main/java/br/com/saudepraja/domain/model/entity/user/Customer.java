package br.com.saudepraja.domain.model.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Table
@Entity(name= "customer")
public class Customer {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Users users;

    @Column(name = "telephone", length = 11, insertable = true, nullable = false)
    private String telephone;

    @Column(name = "cpf", length = 11, insertable = true, nullable = false)
    private String cpf;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Column(name = "upload_date")
    private LocalDateTime update;

    @Version
    private Integer version;

    public Customer(Users users, String telephone, String cpf, LocalDate birthday) {
        this.users = users;
        this.telephone = telephone;
        this.cpf = cpf;
        this.birthday = birthday;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    public static class Builder {
        private Users users;
        private String telephone;
        private String cpf;
        private LocalDate birthday;

        public Builder with(Users users) {
            this.users = users;
            return this;
        }

        public Builder withTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Customer build() {
            return new Customer(this.getUsers(), this.getTelephone(), this.getCpf(), this.getBirthday());
        }

    }
}
