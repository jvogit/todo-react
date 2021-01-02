package com.github.jvogit.todoreact.controllers.todos;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.jwt.JwtUserPrincipal;
import com.github.jvogit.todoreact.payloads.TodoCreatePayload;
import com.github.jvogit.todoreact.services.TodoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/todos")
@Slf4j
public class TodosController {
    @Autowired
    private TodoService todoService;
    
    @PostMapping
    public Todo post(@AuthenticationPrincipal JwtUserPrincipal user, @Valid @RequestBody TodoCreatePayload payload) {
        log.info("Called");
        return todoService.createTodo(user.getId(), payload);
    }
    
    @GetMapping
    public Todo get(@AuthenticationPrincipal JwtUserPrincipal user, @RequestParam String date) {
        return todoService.getTodo(user.getId(), LocalDate.parse(date)).orElse(new Todo());
    }
}
