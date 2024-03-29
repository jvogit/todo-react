package com.github.jvogit.todoreact.controller;

import com.github.jvogit.todoreact.model.JwtUserDetails;
import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.input.TodoInput;
import com.github.jvogit.todoreact.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.github.jvogit.todoreact.util.AuthUtil.getUserDetails;

@Controller
public class TodoController {

    private static final Logger log = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoService;

    public TodoController(final TodoService todoService) {
        this.todoService = todoService;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<Todo> todos() {
        final JwtUserDetails userDetails = getUserDetails();

        return todoService.todos(userDetails.getId());
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo createTodo(@Argument("item") final String item) {
        final JwtUserDetails userDetails = getUserDetails();

        return todoService.createTodo(UUID.randomUUID(), userDetails.getId(), item);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo updateTodo(@Argument("todo") final TodoInput todo) {
        final JwtUserDetails userDetails = getUserDetails();

        return todoService.updateTodo(todo.id(), userDetails.getId(), todo.item(), todo.completed());
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean deleteTodo(@Argument("id") final UUID id) {
        final JwtUserDetails userDetails = getUserDetails();

        todoService.deleteTodo(id, userDetails.getId());

        return true;
    }
}
