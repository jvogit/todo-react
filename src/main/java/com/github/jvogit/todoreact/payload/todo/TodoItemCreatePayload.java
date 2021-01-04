package com.github.jvogit.todoreact.payload.todo;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TodoItemCreatePayload {
    @NotNull
    private final LocalDate date;
    @NotNull
    private final Boolean completed;
    @NotNull
    private final String text;
}
