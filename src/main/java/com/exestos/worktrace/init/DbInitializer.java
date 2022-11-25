package com.exestos.worktrace.init;

import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DbInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("password"));
        user.setAdmin(true);
        userRepository.save(user);
    }
}
