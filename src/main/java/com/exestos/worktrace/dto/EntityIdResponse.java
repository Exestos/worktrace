package com.exestos.worktrace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityIdResponse {

    private long id;

    public static EntityIdResponse of(long id) {
        return new EntityIdResponse(id);
    }
}
