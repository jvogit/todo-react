package com.github.jvogit.todoreact.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.entities.todos.TodoItem;
import com.github.jvogit.todoreact.entities.todos.TodoKey;
import com.github.jvogit.todoreact.payloads.TodoCreateItemPayload;
import com.github.jvogit.todoreact.payloads.TodoCreatePayload;
import com.github.jvogit.todoreact.payloads.TodoUpdateItemPayload;
import com.github.jvogit.todoreact.repository.TodoItemRepository;
import com.github.jvogit.todoreact.repository.TodoRepository;

@Service
@Transactional
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    public Optional<Todo> getTodo(Long user_id, LocalDate date) {
        return todoRepository.findById(new TodoKey(user_id, date)).map(i -> { i.getItems(); return i; });
    }

    public Todo createTodo(Long user_id, TodoCreatePayload payload) {
        Todo todo = todoRepository
                .findById(new TodoKey(user_id, payload.getDate()))
                .orElseGet(() -> new Todo(user_id, payload.getDate()));

        return todoRepository.save(todo);
    }

    public TodoItem createTodoItem(Long user_id, TodoCreateItemPayload payload) {
        TodoItem item = new TodoItem(payload.getText(), payload.getCompleted());
        Todo todo = todoRepository.findById(new TodoKey(user_id, payload.getDate())).orElseThrow();
        todo.addItem(item);
        todoRepository.save(todo);
        
        return item;
    }
    
    public TodoItem updateTodoItem(Long user_id, TodoUpdateItemPayload payload) {
        TodoItem item = todoItemRepository.findByUserIdAndId(user_id, payload.getId()).orElseThrow();
        
        if (payload.getIndex() != null && item.getIndex() != payload.getIndex()) {
            Todo todo = item.getTodo();

            todo.getItems().remove(item);
            todo.getItems().add(payload.getIndex(), item);
            item.setIndex(payload.getIndex());
            
            todoRepository.save(todo);
            
            return item;
        } else {
            item.setText(payload.getText());
            item.setCompleted(payload.getCompleted());
        }
        
        return todoItemRepository.save(item);
    }

    public void deleteItem(Long user_id, TodoUpdateItemPayload payload) {
        TodoItem item = todoItemRepository.findByUserIdAndId(user_id, payload.getId()).orElseThrow();
        Todo todo = item.getTodo();
        todo.getItems().remove(item);
        todoRepository.save(todo);
    }

}
