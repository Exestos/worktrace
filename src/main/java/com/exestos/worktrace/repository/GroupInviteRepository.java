package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.GroupInvite;
import com.exestos.worktrace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long> {
    List<GroupInvite> findGroupInviteByUser(User user);
}
