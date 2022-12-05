package com.exestos.worktrace.dto.group;

import lombok.Data;

@Data
public class GroupInviteRequest {
    private long user_id;
    private long group_id;
}
