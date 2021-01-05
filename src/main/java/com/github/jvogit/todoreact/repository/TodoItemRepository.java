package com.github.jvogit.todoreact.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entity.todo.TodoItem;

@Repository
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    Optional<TodoItem> findByTodo_User_IdAndId(Long user_id, Long id);
    long deleteByTodo_User_IdAndId(Long user_id, Long id);
}
