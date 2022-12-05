package com.exestos.worktrace.repository;

import com.exestos.worktrace.domain.GroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, Long> {

    interface GroupInvites {
        UserRepository.UserInfo getUser();

        UserRepository.UserInfo getFrom();
    }

    interface UserInvites {
        GroupRepository.GroupTitle getGroup();

        UserRepository.UserInfo getFrom();
    }

    List<GroupInvites> findGroupInvitesByGroupId(long group_id);

    List<UserInvites> findUserInvitesByUserId(long user_id);

}
