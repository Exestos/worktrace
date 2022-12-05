package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRoleRepository extends JpaRepository<GroupRole, Long> {

    interface RolePrivileges {
        Long getPrivileges();
    }

    List<RolePrivileges> findGroupRolesByGroupIdAndUsersId(long group_id, long user_id);
}
