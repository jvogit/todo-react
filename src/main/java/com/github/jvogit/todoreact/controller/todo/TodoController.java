package com.github.jvogit.todoreact.controller.todo;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jvogit.todoreact.entity.todo.Todo;
import com.github.jvogit.todoreact.entity.todo.TodoItem;
import com.github.jvogit.todoreact.jwt.JwtUserPrincipal;
import com.github.jvogit.todoreact.payload.todo.TodoItemCreatePayload;
import com.github.jvogit.todoreact.payload.todo.TodoItemUpdateIndexPayload;
import com.github.jvogit.todoreact.payload.todo.TodoItemUpdatePayload;
import com.github.jvogit.todoreact.response.ApiSuccess;
import com.github.jvogit.todoreact.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public Todo get(@AuthenticationPrincipal JwtUserPrincipal user,
            @RequestParam String date) {
        return todoService.getTodo(user.getId(), LocalDate.parse(date));
    }

    @PostMapping("/item")
    public TodoItem createItem(@AuthenticationPrincipal JwtUserPrincipal user,
            @Valid @RequestBody TodoItemCreatePayload payload) {
        return todoService.createTodoItem(user.getId(), payload);
    }

    @PutMapping("/item/{id}")
    public TodoItem putItem(@AuthenticationPrincipal JwtUserPrincipal user,
            @PathVariable Long id,
            @Valid @RequestBody TodoItemUpdatePayload payload) {
        return todoService.updateTodoItem(user.getId(), id, payload);
    }

    @PutMapping("/item/{id}/index")
    public TodoItem putItemIndex(@AuthenticationPrincipal JwtUserPrincipal user,
            @PathVariable Long id,
            @Valid @RequestBody TodoItemUpdateIndexPayload payload) {
        return todoService.updateTodoItemIndex(user.getId(), id, payload);
    }

    @DeleteMapping("/item/{id}/**")
    public ApiSuccess deleteItem(@AuthenticationPrincipal JwtUserPrincipal user,
            @PathVariable Long id) {
        todoService.deleteItem(user.getId(), id);

        return new ApiSuccess();
    }

}
