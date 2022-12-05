package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.EntityIdResponse;
import com.exestos.worktrace.dto.group.GroupCreateRequest;
import com.exestos.worktrace.service.group.GroupService;
import com.exestos.worktrace.service.security.AuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final AuthorityService authorityService;

    public GroupController(GroupService groupService, AuthorityService authorityService) {
        this.groupService = groupService;
        this.authorityService = authorityService;
    }

    @PostMapping
    public ResponseEntity<EntityIdResponse> create(@RequestBody GroupCreateRequest request, Principal principal) {
        User user = authorityService.getUser(principal);
        Group group = groupService.createGroup(request.getTitle(), user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntityIdResponse.of(group.getId()));
    }
}
