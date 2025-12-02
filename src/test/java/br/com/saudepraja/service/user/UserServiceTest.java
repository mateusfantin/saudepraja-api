package br.com.saudepraja.service.user;

import br.com.saudepraja.domain.model.entity.user.User;
import br.com.saudepraja.domain.model.entity.user.Users;
import br.com.saudepraja.domain.model.entity.user.dto.UserDTO;
import br.com.saudepraja.domain.model.entity.user.enumeration.UserTypeEnum;
import br.com.saudepraja.domain.model.repository.user.UsersRepository;
import br.com.saudepraja.domain.user.CustomerService;
import br.com.saudepraja.domain.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private Users users;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        users = UserMock.existentUser(UserTypeEnum.CUSTOMER);
    }

    @Test
    public void Dado_id_usuario_Quando_valido_Entao_exclua() throws Exception {
        Mockito.when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.delete(10L);
        Mockito.verify(usersRepository).delete(any());
    }

    @Test
    public void Dado_criacao_usuario_Quando_informado_usuario_valido_Entao_cadastre() {
        userService.save(UserDTOMock.newUserDTO(10L, UserTypeEnum.CUSTOMER));
        Mockito.verify(usersRepository).save(any(Users.class));
    }

    @Test
    public void Dado_criacao_usuario_Quando_usuarioDTO_null_Then_dispara_excecao() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userService.save(null);
        });

        Mockito.verifyNoInteractions(usersRepository);
    }

    @Test
    public void Dado_atualizacao_usuario_Quando_atualizar_objeto_Then_salvar() throws Exception {
        UserDTO userDTO = UserDTOMock.newUserDTO(10L, UserTypeEnum.CUSTOMER);
        Mockito.when(usersRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDTO userDTOResponse = userService.update(userDTO);
        Mockito.verify(usersRepository).save(any());
        Assertions.assertAll(() -> {
            Assertions.assertEquals(userDTO.getEmail(), userDTOResponse.getEmail());
            Assertions.assertEquals(userDTO.getName(), userDTOResponse.getName());
        });
    }
}
