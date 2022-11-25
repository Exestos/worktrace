package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.jwt.LoginRequest;
import com.exestos.worktrace.dto.jwt.RegistrationRequest;
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
    public ResponseEntity<String> token(@RequestBody LoginRequest userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokenService.generateToken(authentication));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request, Principal principal) {
        if (!authorityService.isAdmin(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registrationService.register(request));
    }
}
