package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    interface UserInfo {
        Long getId();
        String getUsername();
        String getFirstName();
        String getLastName();
        boolean isAdmin();
        Set<GroupRepository.GroupTitle> getGroups();
    }

    UserInfo findUserInfoById(Long id);
    User findUserByUsername(String username);
}
