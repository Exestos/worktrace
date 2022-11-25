package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

    interface GroupTitle {
        Long getId();
        String getTitle();
    }
}
