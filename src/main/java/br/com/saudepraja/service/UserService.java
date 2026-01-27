package br.com.saudepraja.service;

import br.com.saudepraja.domain.exception.SaudePrajaBusinessException;
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

    public Users findUsersById(final Long userId) throws SaudePrajaBusinessException {
        return usersRepository.findById(userId).orElseThrow(() -> new SaudePrajaBusinessException("Users not found. Id:" + userId));
    }

    public UserDTO save(UserDTO userDTO) {
        Objects.requireNonNull(userDTO);

        Users user = Users.builder()
            .withName(userDTO.getName())
            .withEmail(userDTO.getEmail())
            .withPassword(passwordEncoder.encode(userDTO.getPassword()))
            .withUserType(userDTO.getUserType()).build();

        usersRepository.save(user);

        if(userDTO.getUserType().isCustomer()) {
            customerService.save(user, userDTO.getCustomerDTO());
        }

        if(userDTO.getUserType().isMedic()) {
            medicService.save(user, userDTO.getMedicDTO());
        }

        return this.buildUserDTO(user);
    }

    public UserDTO update(UserDTO userDTO) throws Exception {
        Objects.requireNonNull(userDTO);
        if(userDTO.getId() == null) {
            throw new IllegalArgumentException("Please inform the Id");
        }

        Users user = this.findUsersById(userDTO.getId());
        if(userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if(userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        usersRepository.save(user);
        return this.buildUserDTO(user);
    }

    private UserDTO buildUserDTO(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public void delete(final Long userId) throws Exception {
        Objects.requireNonNull(userId);
        Users users = this.findUsersById(userId);
        usersRepository.delete(users);
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
