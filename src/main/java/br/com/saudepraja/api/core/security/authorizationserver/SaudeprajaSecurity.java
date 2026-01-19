package br.com.saudepraja.api.core.security.authorizationserver;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SaudeprajaSecurity {

    public static Authentication getCredentials() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
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
