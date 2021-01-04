package com.github.jvogit.todoreact.payload.todo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TodoItemUpdateIndexPayload {
    @NotNull
    private Integer index;
}
