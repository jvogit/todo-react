package com.github.jvogit.todoreact.payloads;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TodoCreatePayload {
    private final LocalDate date;
    private final List<TodoCreateItemPayload> items;
}
