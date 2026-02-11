package br.com.saudepraja.api.controller;

import br.com.saudepraja.api.core.security.authorizationserver.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "/Auth", description = "Auth management")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid body"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "500", description = "Server Error")
})
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(summary = "Authenticates the User and returns a JWT token", description = "Authenticates the User and returns a JWT token")
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication auth = authenticationManager.authenticate(authToken);

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    public record LoginDto(String email, String password) {}
    public record TokenResponse(String token) {}
}
