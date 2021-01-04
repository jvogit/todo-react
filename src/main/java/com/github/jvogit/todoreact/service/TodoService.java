package com.github.jvogit.todoreact.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jvogit.todoreact.entity.todo.Todo;
import com.github.jvogit.todoreact.entity.todo.TodoItem;
import com.github.jvogit.todoreact.entity.todo.TodoKey;
import com.github.jvogit.todoreact.payload.todo.TodoItemCreatePayload;
import com.github.jvogit.todoreact.payload.todo.TodoItemUpdateIndexPayload;
import com.github.jvogit.todoreact.payload.todo.TodoItemUpdatePayload;
import com.github.jvogit.todoreact.repository.TodoItemRepository;
import com.github.jvogit.todoreact.repository.TodoRepository;

@Service
@Transactional
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

    public Todo getTodo(Long user_id, LocalDate date) {
        return todoRepository.findById(new TodoKey(user_id, date))
                .orElseGet(() -> todoRepository.save(new Todo(user_id, date)));
    }
    
    public TodoItem createTodoItem(Long user_id, TodoItemCreatePayload payload) {
        TodoItem item = new TodoItem(payload.getText(), payload.getCompleted());
        Todo todo = todoRepository.findById(new TodoKey(user_id, payload.getDate())).orElseThrow();
        todo.addItem(item);
        todo = todoRepository.save(todo);
        
        return todo.getItems().get(todo.getItems().size() - 1);
    }
    
    public TodoItem updateTodoItem(Long user_id, Long item_id, TodoItemUpdatePayload payload) {
        TodoItem item = todoItemRepository.findByTodo_User_IdAndId(user_id, item_id).orElseThrow();
        item.setText(payload.getText());
        item.setCompleted(payload.getCompleted());
        
        return todoItemRepository.save(item);
    }
    
    public TodoItem updateTodoItemIndex(Long user_id, Long item_id, TodoItemUpdateIndexPayload payload) {
        TodoItem item = todoItemRepository.findByTodo_User_IdAndId(user_id, item_id).orElseThrow();
        Todo todo = item.getTodo();
        
        todo.getItems().remove(item.getIndex().intValue());
        todo.getItems().add(payload.getIndex(), item);
        item.setIndex(payload.getIndex());
        
        todoRepository.save(todo);
        
        return item;
    }

    public void deleteItem(Long user_id, Long id) {
        TodoItem item = todoItemRepository.findByTodo_User_IdAndId(user_id, id).orElseThrow();
        Todo todo = item.getTodo();
        todo.getItems().remove(item);
        
        todoRepository.save(todo);
    }

}
