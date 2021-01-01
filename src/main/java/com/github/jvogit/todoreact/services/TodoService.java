package com.github.jvogit.todoreact.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.payloads.TodoCreatePayload;
import com.github.jvogit.todoreact.repository.TodoRepository;
import com.github.jvogit.todoreact.repository.UserRepository;

@Service
@Transactional
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    
    public Optional<Todo> getTodo(Long userId, LocalDate date) {
        return todoRepository.findByUserIdAndDate(userId, date);
    }
    
    public Todo createTodo(Long user_id, TodoCreatePayload payload) {
        Todo todo = new Todo(userRepository.findById(user_id).get(), LocalDate.parse(payload.getDate()));
        payload.getItems().forEach(todo::addItem);
        
        return todo;
    }
    
}
