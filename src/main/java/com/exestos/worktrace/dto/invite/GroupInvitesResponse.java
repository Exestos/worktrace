package com.exestos.worktrace.dto.invite;

import com.exestos.worktrace.repository.GroupInviteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupInvitesResponse {
    private List<GroupInviteRepository.GroupInvites> invites;
}
