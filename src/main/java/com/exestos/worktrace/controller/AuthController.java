package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.EntityIdResponse;
import com.exestos.worktrace.dto.jwt.LoginRequest;
import com.exestos.worktrace.dto.jwt.RegistrationRequest;
import com.exestos.worktrace.dto.jwt.TokenResponse;
import com.exestos.worktrace.service.security.TokenService;
import com.exestos.worktrace.service.security.AuthorityService;
import com.exestos.worktrace.service.user.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final AuthorityService authorityService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager,
                          RegistrationService registrationService, AuthorityService authorityService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
        this.authorityService = authorityService;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(@RequestBody LoginRequest userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
        String token = tokenService.generateToken(authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<EntityIdResponse> register(@RequestBody RegistrationRequest request, Principal principal) {
        if (!authorityService.isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        User user = registrationService.register(
                request.getUsername(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityIdResponse.of(user.getId()));
    }
}
