package com.github.jvogit.todoreact.service;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.repository.TodoRepository;
import com.github.jvogit.todoreact.repository.UserRepository;
import com.github.jvogit.todoreact.util.AuthUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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

    public Todo updateTodo(final UUID id, final UUID userId, final String item, final boolean completed) {
        final Todo todo = todoRepository.findById(id)
                .filter(o -> AuthUtil.authorize(o, userId))
                .orElseThrow();

        todo.setItem(item);
        todo.setCompleted(completed);

        return todoRepository.save(todo);
    }
}
