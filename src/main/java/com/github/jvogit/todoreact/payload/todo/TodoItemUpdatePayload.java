package com.github.jvogit.todoreact.payload.todo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TodoItemUpdatePayload {
    @NotNull
    private final String text;
    @NotNull
    private final Boolean completed;
}
