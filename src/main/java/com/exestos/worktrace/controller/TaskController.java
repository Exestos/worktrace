package com.exestos.worktrace.controller;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.Task;
import com.exestos.worktrace.dto.task.TaskRequest;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.TaskRepository;
import com.exestos.worktrace.service.security.AuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final AuthorityService authorityService;

    public TaskController(TaskRepository taskRepository, GroupRepository groupRepository, AuthorityService authorityService) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.authorityService = authorityService;
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(value -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(value))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TaskRequest request, Principal principal) {
        Group group = groupRepository.getReferenceById(request.group_id());
        if (!authorityService.canCreateTask(principal, group)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setGroup(group);
        task.setUser(authorityService.getUser(principal));
        task = taskRepository.save(task);
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("id", task.getId());
        body.put("title", task.getTitle());
        body.put("description", task.getDescription());
        body.put("group_id", task.getGroup().getId());
        body.put("creator_id", task.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }
}
