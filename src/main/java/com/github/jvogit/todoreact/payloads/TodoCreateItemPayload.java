package com.github.jvogit.todoreact.payloads;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TodoCreateItemPayload {
    @NotNull
    private final LocalDate date;
    @NotNull
    private final Boolean completed;
    @NotNull
    private final String text;
}
