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
    public void send_invite(GroupInviteRequest request, User from) {
        Group group = groupRepository.findById(request.getGroup_id()).get();
        User user = userRepository.findById(request.getUser_id()).get();
        GroupInvite groupInvite = new GroupInvite();
        groupInvite.setGroup(group);
        groupInvite.setUser(user);
        groupInvite.setFrom(from);
        groupInviteRepository.save(groupInvite);
    }

    public List<GroupInvite> getUserInvites(User user) {
        return groupInviteRepository.findGroupInviteByUser(user);
    }

    public void accept_invite(Long invite_id, User user) {
        GroupInvite groupInvite = groupInviteRepository.findById(invite_id).get();
        if (!user.getId().equals(groupInvite.getUser().getId())){
            return;
        }
        Group group = groupRepository.findById(groupInvite.getId()).get();
        group.getUsers().add(user);
        groupRepository.save(group);
    }
}
