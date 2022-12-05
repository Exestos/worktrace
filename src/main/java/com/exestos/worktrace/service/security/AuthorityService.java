package com.exestos.worktrace.service.security;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.GroupRoleRepository;
import com.exestos.worktrace.repository.UserRepository;
import com.exestos.worktrace.utils.GRolePrivileges;
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

    public boolean hasPrivilege(Long user_id, Long group_id, int privilege) {
        var rolePrivilegesList = groupRoleRepository.findGroupRolesByGroupIdAndUsersId(group_id, user_id);
        for (var rolePrivileges : rolePrivilegesList) {
            GRolePrivileges privileges = new GRolePrivileges(rolePrivileges.getPrivileges());
            if (privileges.hasPrivilege(privilege)) return true;
        }
        return false;
    }

//    public Set<GroupPrivilege> getGroupPrivileges(Long user_id, Long group_id) {
//        Set<GroupPrivilege> groupPrivileges = new HashSet<>();
//        List<GroupRole> groupRoles = groupRoleRepository.findGroupRolesByGroup_IdAndUsers_Id(user_id, group_id);
//        for (GroupRole groupRole : groupRoles) {
//            groupPrivileges.addAll(groupRole.getPrivileges());
//        }
//        return groupPrivileges;
//    }

    public boolean canCreateTask(Principal principal, Group group) {
        return true;
    }

    @Transactional
    public boolean canInvite(Group group, User sender) {
        return true;
//        return sender.getRoles().stream().anyMatch(
//                role -> role.getGroup().getId().equals(group.getId())
//                        && role.getPrivileges().contains(GroupPrivilege.INVITE));
    }


}
