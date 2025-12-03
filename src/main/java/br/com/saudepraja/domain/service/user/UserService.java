package br.com.saudepraja.domain.service.user;

import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO;
import br.com.saudepraja.domain.model.repository.user.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MedicService medicService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Users findUsersById(final Long id) throws Exception {
        return usersRepository.findById(id).orElseThrow(() -> new Exception("Users not found. Id:" + id));
    }

    public UserDTO save(UserDTO userDTO) {
        Objects.requireNonNull(userDTO);

        Users users = Users.builder()
            .withName(userDTO.getName())
            .withEmail(userDTO.getEmail())
            .withPassword(passwordEncoder.encode(userDTO.getPassword()))
            .withUserType(userDTO.getUserType()).build();

        usersRepository.save(users);

        if(userDTO.getUserType().isCustomer()) {
            customerService.save(users, userDTO.getCustomerDTO());
        }

        if(userDTO.getUserType().isMedic()) {
            medicService.save(users, userDTO.getMedicDTO());
        }

        return this.buildUserDTO(users);
    }

    public UserDTO update(UserDTO userDTO) throws Exception {
        Objects.requireNonNull(userDTO);
        if(userDTO.getId() == null) {
            throw new IllegalArgumentException("Please inform the Id");
        }

        Users users = this.findUsersById(userDTO.getId());
        if(userDTO.getName() != null) {
            users.setName(userDTO.getName());
        }
        if(userDTO.getEmail() != null) {
            users.setEmail(userDTO.getEmail());
        }

        usersRepository.save(users);
        return this.buildUserDTO(users);
    }

    private UserDTO buildUserDTO(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setName(users.getName());
        userDTO.setEmail(users.getEmail());
        return userDTO;
    }

    public void delete(final Long usersId) throws Exception {
        Objects.requireNonNull(usersId);
        Users users = this.findUsersById(usersId);
        usersRepository.delete(users);
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
