package com.github.jvogit.todoreact.service;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.repository.TodoRepository;
import com.github.jvogit.todoreact.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(final UserRepository userRepository, final TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public List<Todo> todos(final UUID userId) {
        return userRepository.findById(userId).orElseThrow().getTodos();
    }

    public Todo createTodo(final UUID id, final UUID userId, final String item) {
        final User user = userRepository.findById(userId).orElseThrow();
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
        final User user = User.builder().id(userId).build();
        final Todo todo = todoRepository.findByIdAndUser(id, user).orElseThrow();
        todo.setItem(item);
        todo.setCompleted(completed);

        return todoRepository.save(todo);
    }

    public void deleteTodo(final UUID id, final UUID userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final Todo todo = user.getTodos().stream()
                .filter(o -> id.equals(o.getId()))
                .findAny()
                .orElseThrow();

        user.getTodos().remove(todo);

        userRepository.save(user);
    }
}
