package com.github.jvogit.todoreact.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.entities.todos.TodoItem;
import com.github.jvogit.todoreact.payloads.TodoCreatePayload;
import com.github.jvogit.todoreact.repository.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Optional<Todo> getTodo(Long userId, LocalDate date) {
        return todoRepository.findByUserIdAndDate(userId, date);
    }

    public Todo createTodo(Long user_id, TodoCreatePayload payload) {
        Todo todo = todoRepository.findByUserIdAndDate(user_id,
                LocalDate.parse(payload.getDate())).get();
        var items = payload.getItems().stream().map(
                item -> new TodoItem(item.getId(), item.getText(), item.getCompleted()))
                .collect(Collectors.toList());
        items.forEach(item -> log.info(item.getText()));
        todo.setItems(items);
        
        return todoRepository.save(todo);
    }

}
