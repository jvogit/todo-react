package com.github.jvogit.todoreact.payloads;

import java.util.List;

import com.github.jvogit.todoreact.entities.todos.TodoItem;

import lombok.Data;

@Data
public class TodoCreatePayload {
    private final String date;
    private final List<TodoItem> items;
}
