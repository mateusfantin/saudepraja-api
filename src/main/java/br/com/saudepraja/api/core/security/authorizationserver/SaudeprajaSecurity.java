package br.com.saudepraja.api.core.security.authorizationserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SaudeprajaSecurity {

    public static Authentication getCredentials() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static boolean haveCredentialsByRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals("ROLE_ADMIN"));
    }

    public static boolean isAdmin() {
        return haveCredentialsByRole("ROLE_ADMIN");
    }

    public static boolean isCustomer() {
        return haveCredentialsByRole("ROLE_CUSTOMER");
    }
}
