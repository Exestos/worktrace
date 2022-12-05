package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.Task;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.dto.task.TaskRequest;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.TaskRepository;
import com.exestos.worktrace.service.security.AuthorityService;
import com.exestos.worktrace.service.task.TaskService;
import com.exestos.worktrace.utils.GRolePrivileges;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final AuthorityService authorityService;

    public TaskController(TaskService taskService, AuthorityService authorityService) {
        this.taskService = taskService;
        this.authorityService = authorityService;
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TaskRequest request, Principal principal) {
        User user = authorityService.getUser(principal);
        if (!authorityService.hasPrivilege(user.getId(), request.getGroup_id(), GRolePrivileges.CREATE_TASK)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Object task = taskService.create(user, request.getGroup_id(), request.getTitle(), request.getDescription());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(task);
    }
}
