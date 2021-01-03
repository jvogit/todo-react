package com.github.jvogit.todoreact.payloads;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TodoUpdateItemPayload {
    @NotNull
    private final Long id; 
    @NotNull
    private final String text;
    @NotNull
    private final Boolean completed;
    private final Integer index;
}
