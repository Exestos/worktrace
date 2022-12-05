package com.exestos.worktrace.service.group;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.GroupRole;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.GroupRoleRepository;
import com.exestos.worktrace.utils.GRolePrivileges;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;

    public GroupService(GroupRepository groupRepository, GroupRoleRepository groupRoleRepository) {
        this.groupRepository = groupRepository;
        this.groupRoleRepository = groupRoleRepository;
    }

    @Transactional
    public Group createGroup(String title, User creator) {
        Group group = new Group();
        group.setTitle(title);
        group.setCreator(creator);
        group.setUsers(Set.of(creator));
        group = groupRepository.save(group);
        // Set default roles
        GroupRole groupRole = new GroupRole();
        groupRole.setRoleName("Creator");
        groupRole.setGroup(group);
        groupRole.setPrivileges(GRolePrivileges.creatorPrivileges());
        groupRole.setUsers(List.of(creator));
        groupRoleRepository.save(groupRole);

        groupRole = new GroupRole();
        groupRole.setRoleName("Default");
        groupRole.setGroup(group);
        groupRole.setPrivileges(GRolePrivileges.defaultPrivileges());
        groupRoleRepository.save(groupRole);

        return group;
    }
}
