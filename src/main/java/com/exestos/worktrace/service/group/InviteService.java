package com.exestos.worktrace.service.group;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.GroupInvite;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.group.GroupInviteRequest;
import com.exestos.worktrace.repository.GroupInviteRepository;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InviteService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupInviteRepository groupInviteRepository;

    public InviteService(UserRepository userRepository, GroupRepository groupRepository, GroupInviteRepository groupInviteRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupInviteRepository = groupInviteRepository;
    }

    @Transactional
    public GroupInvite send_invite(long group_id, long user_id, User from) {
        Optional<Group> group = groupRepository.findById(group_id);
        Optional<User> user = userRepository.findById(user_id);
        if (group.isEmpty() || user.isEmpty()) {
            return null;
        }
        GroupInvite groupInvite = new GroupInvite();
        groupInvite.setGroup(group.get());
        groupInvite.setUser(user.get());
        groupInvite.setFrom(from);
        return groupInviteRepository.save(groupInvite);
    }

    public List<GroupInviteRepository.UserInvites> getUserInvites(long user_id) {
        return groupInviteRepository.findUserInvitesByUserId(user_id);
    }

    public List<GroupInviteRepository.GroupInvites> getGroupInvites(long group_id) {
        return groupInviteRepository.findGroupInvitesByGroupId(group_id);
    }

    public Group accept_invite(Long invite_id, User user) {
        Optional<GroupInvite> groupInvite = groupInviteRepository.findById(invite_id);
        if (groupInvite.isEmpty() || !user.getId().equals(groupInvite.get().getUser().getId())){
            return null;
        }
        // todo: make it work correctly
        Group group = groupRepository.findById(groupInvite.get().getId()).get();
        group.getUsers().add(user);
        groupRepository.save(group);
        return group;
    }
}
