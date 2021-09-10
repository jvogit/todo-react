package com.github.jvogit.todoreact.controller;

import com.github.jvogit.todoreact.model.JwtUserDetails;
import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.model.input.TodoInput;
import com.github.jvogit.todoreact.service.TodoService;
import com.github.jvogit.todoreact.service.UserService;
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
    private final UserService userService;
    private final TodoService todoService;

    public TodoController(final UserService userService, final TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<Todo> todos() {
        final JwtUserDetails userDetails = getUserDetails();
        final User user = userService.getUser(userDetails.getId()).orElseThrow();

        return user.getTodos();
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo createTodo(@Argument("item") final String item) {
        final JwtUserDetails userDetails = getUserDetails();
        final User user = userService.getUser(userDetails.getId()).orElseThrow();

        return todoService.createTodo(user, UUID.randomUUID(), item);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo updateTodo(@Argument("todo") final TodoInput todo) {
        final JwtUserDetails userDetails = getUserDetails();

        return todoService.updateTodo(todo.id(), userDetails.toUser(), todo.item(), todo.completed());
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Todo deleteTodo(@Argument("todo") final TodoInput todo) {
        final JwtUserDetails userDetails = getUserDetails();

        return todoService.deleteTodo(todo.id(), userDetails.toUser());
    }
}
