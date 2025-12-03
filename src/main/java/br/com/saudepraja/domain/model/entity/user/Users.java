package br.com.saudepraja.domain.model.entity.user;

import br.com.saudepraja.domain.model.entity.user.enumeration.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 150, insertable = true, nullable = false)
    private String name;

    @Column(name = "email", length = 150, insertable = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 9, insertable = true, nullable = false)
    private UserTypeEnum userType;

    @OneToOne(mappedBy = "users")
    private Customer customer;

    @OneToOne(mappedBy = "users")
    private Medic medic;

    @Column(name = "password", length = 100)
    private String password;

    public Users(Long id) {
        this.id = id;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Getter
    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private UserTypeEnum userType;
        private Customer customer;
        private Medic medic;
        private String password;

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

        public Builder withUserType(UserTypeEnum userType) {
            this.userType = userType;
            return this;
        }

        public Builder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder withMedic(Medic medic) {
            this.medic = medic;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Users build() {
            return new Users(this.getId(), this.getName(), this.getEmail(), this.getUserType(), this.getCustomer(), this.getMedic(),
                    this.getPassword()
            );
        }

    }
}
