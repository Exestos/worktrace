package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.GroupInvite;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.group.GroupInviteRequest;
import com.exestos.worktrace.service.group.InviteService;
import com.exestos.worktrace.service.security.AuthorityService;
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
    public ResponseEntity<List<GroupInvite>> invite(Principal principal) {
        User user = authorityService.getUser(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(inviteService.getUserInvites(user));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> invite(@RequestBody GroupInviteRequest request, Principal principal) {
        if (!authorityService.canInvite(request, principal)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        inviteService.send_invite(request, authorityService.getUser(principal));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @GetMapping("/accept/{invite_id}")
    public ResponseEntity<Boolean> accept(@PathVariable Long invite_id, Principal principal) {
        inviteService.accept_invite(invite_id, authorityService.getUser(principal));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }
}
