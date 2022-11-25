package com.exestos.worktrace.controller;

import com.exestos.worktrace.repository.UserRepository;
import com.exestos.worktrace.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<UserRepository.UserInfo> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findInfo(id));
    }


}
