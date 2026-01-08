package br.com.saudepraja.api.core.security.authorizationserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {

    private String secret;

    private Long expiration;
}
