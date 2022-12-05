package com.exestos.worktrace.dto.task;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private long group_id;
}
