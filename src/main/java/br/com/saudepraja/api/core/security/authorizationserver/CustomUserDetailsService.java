package br.com.saudepraja.api.core.security.authorizationserver;

import br.com.saudepraja.domain.model.user.Users;
import br.com.saudepraja.domain.repository.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UsersRepository repository;

    public CustomUserDetailsService(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsInfo loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UserDetailsInfo(user.getId(), user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + user.getUserType()));
    }


}
