package com.exestos.worktrace.service.task;

import com.exestos.worktrace.domain.Group;
import com.exestos.worktrace.domain.Task;
import com.exestos.worktrace.domain.User;
import com.exestos.worktrace.repository.GroupRepository;
import com.exestos.worktrace.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class TaskService
{
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;

    public TaskService(GroupRepository groupRepository, TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
    }

    public Task findById(long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            return null;
        }
        return task.get();
    }

    public Object create(User creator, long group_id, String title, String description) {
        Group group = groupRepository.getReferenceById(group_id);
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setGroup(group);
        task.setUser(creator);
        task = taskRepository.save(task);
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("id", task.getId());
        body.put("title", task.getTitle());
        body.put("description", task.getDescription());
        body.put("group_id", task.getGroup().getId());
        body.put("creator_id", task.getUser().getId());
        return body;
    }
}
