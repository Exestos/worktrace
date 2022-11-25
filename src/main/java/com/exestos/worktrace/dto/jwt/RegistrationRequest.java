package com.exestos.worktrace.dto.jwt;

public record RegistrationRequest(
        String username,
        String password,
        String firstName,
        String lastName) {
}
