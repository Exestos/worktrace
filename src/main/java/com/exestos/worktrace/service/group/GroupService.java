package com.exestos.worktrace.service.group;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.group.GroupCreateRequest;
import com.exestos.worktrace.repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Group createGroup(GroupCreateRequest request, User user) {
        Group group = new Group();
        group.setTitle(request.getTitle());
        group.setCreator(user);
        group.setUsers(Set.of(user));
        return groupRepository.save(group);
    }
}
