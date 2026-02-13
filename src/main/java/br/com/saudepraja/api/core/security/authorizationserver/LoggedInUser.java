package br.com.saudepraja.api.core.security.authorizationserver;

import br.com.saudepraja.domain.model.user.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class LoggedInUser implements UserDetails {

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    public LoggedInUser(Users user) {
        this.username = user.getEmail();
        this.authorities = List.of(new SimpleGrantedAuthority(user.getUserType().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }
}
