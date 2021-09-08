package com.github.jvogit.todoreact.service;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.repository.TodoRepository;
import com.github.jvogit.todoreact.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.UUID;

@Component
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(final UserRepository userRepository, final TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(final User user, final UUID id, final String item) {
        final Todo todo = Todo.builder()
                .id(id)
                .item(item)
                .user(user)
                .build();

        user.getTodos().add(todo);

        userRepository.save(user);

        return todo;
    }

    public Todo updateTodo(final String id, final String item, final boolean completed) {
        final Todo todo = todoRepository.findById(UUID.fromString(id)).orElseThrow();

        todo.setItem(item);
        todo.setCompleted(completed);

        return todoRepository.save(todo);
    }
}
