package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.GroupInvite;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.EntityIdResponse;
import com.exestos.worktrace.dto.group.GroupInviteRequest;
import com.exestos.worktrace.dto.invite.GroupInvitesResponse;
import com.exestos.worktrace.dto.invite.UserInvitesResponse;
import com.exestos.worktrace.service.group.InviteService;
import com.exestos.worktrace.service.security.AuthorityService;
import com.exestos.worktrace.utils.GRolePrivileges;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/invite")
public class GroupInviteController {

    private final AuthorityService authorityService;
    private final InviteService inviteService;

    public GroupInviteController(AuthorityService authorityService, InviteService inviteService) {
        this.authorityService = authorityService;
        this.inviteService = inviteService;
    }

    @GetMapping("/")
    public ResponseEntity<UserInvitesResponse> getUserInvites(Principal principal) {
        User user = authorityService.getUser(principal);
        UserInvitesResponse response = new UserInvitesResponse(inviteService.getUserInvites(user.getId()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/group/{group_id}")
    public ResponseEntity<GroupInvitesResponse> getGroupInvites(@PathVariable Long group_id, Principal principal) {
        User user = authorityService.getUser(principal);
        if (!authorityService.hasPrivilege(user.getId(), group_id, GRolePrivileges.INVITE)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        GroupInvitesResponse response = new GroupInvitesResponse(inviteService.getGroupInvites(group_id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> invite(@RequestBody GroupInviteRequest request, Principal principal) {
        if (!authorityService.hasPrivilege(
                authorityService.getUser(principal).getId(),
                request.getGroup_id(), GRolePrivileges.INVITE)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        GroupInvite groupInvite = inviteService.send_invite(
                request.getGroup_id(),
                request.getUser_id(),
                authorityService.getUser(principal)
        );
        if (groupInvite == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntityIdResponse.of(groupInvite.getId()));
    }

    @GetMapping("/accept/{invite_id}")
    public ResponseEntity<EntityIdResponse> accept(@PathVariable Long invite_id, Principal principal) {
        Group group = inviteService.accept_invite(invite_id, authorityService.getUser(principal));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EntityIdResponse.of(group.getId()));
    }
}
