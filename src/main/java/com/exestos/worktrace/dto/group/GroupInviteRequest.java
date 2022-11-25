package com.exestos.worktrace.dto.group;

import lombok.Data;

@Data
public class GroupInviteRequest {
    private Long user_id;
    private Long group_id;
}
