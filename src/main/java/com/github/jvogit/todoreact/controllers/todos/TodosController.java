package com.github.jvogit.todoreact.controllers.todos;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.entities.todos.TodoItem;
import com.github.jvogit.todoreact.jwt.JwtUserPrincipal;
import com.github.jvogit.todoreact.payloads.TodoCreateItemPayload;
import com.github.jvogit.todoreact.payloads.TodoCreatePayload;
import com.github.jvogit.todoreact.payloads.TodoUpdateItemPayload;
import com.github.jvogit.todoreact.responses.ApiSuccess;
import com.github.jvogit.todoreact.services.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodosController {
    @Autowired
    private TodoService todoService;
    
    @PostMapping
    public Todo post(@AuthenticationPrincipal JwtUserPrincipal user, @Valid @RequestBody TodoCreatePayload payload) {
        return todoService.createTodo(user.getId(), payload);
    }
    
    @GetMapping
    public Todo get(@AuthenticationPrincipal JwtUserPrincipal user, @RequestParam String date) {
        return todoService.getTodo(user.getId(), LocalDate.parse(date)).orElse(new Todo());
    }
    
    @PostMapping("/items")
    public TodoItem postItem(@AuthenticationPrincipal JwtUserPrincipal user, @Valid @RequestBody TodoCreateItemPayload payload) {
        return todoService.createTodoItem(user.getId(), payload);
    }
    
    @PutMapping("/items")
    public TodoItem putItem(@AuthenticationPrincipal JwtUserPrincipal user, @Valid @RequestBody TodoUpdateItemPayload payload) {
        return todoService.updateTodoItem(user.getId(), payload);
    }
    
    @DeleteMapping("/items")
    public ApiSuccess deleteItem(@AuthenticationPrincipal JwtUserPrincipal user, @Valid @RequestBody TodoUpdateItemPayload payload) {
        todoService.deleteItem(user.getId(), payload);
        
        return new ApiSuccess();
    }
    
}
