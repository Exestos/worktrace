package com.exestos.worktrace.service.security;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.GroupPrivilege;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.group.GroupInviteRequest;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.GroupRoleRepository;
import com.exestos.worktrace.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class AuthorityService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;

    public AuthorityService(UserRepository userRepository, GroupRepository groupRepository, GroupRoleRepository groupRoleRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupRoleRepository = groupRoleRepository;
    }

    public User getUser(Principal principal) {
        return userRepository.findUserByUsername(principal.getName());
    }

    public boolean isAdmin(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName());
        return user.isAdmin();
    }

    public boolean canCreateTask(Principal principal, Group group) {
        return true;
    }


    @Transactional
    public boolean canInvite(GroupInviteRequest request, Principal principal) {
        Group group = groupRepository.findById(request.getGroup_id()).get();
        User user = userRepository.findById(request.getUser_id()).get();
        return user.getRoles().stream().anyMatch(
                role -> role.getGroup().getId().equals(group.getId())
                        && role.getPrivileges().contains(GroupPrivilege.INVITE));
    }
}
