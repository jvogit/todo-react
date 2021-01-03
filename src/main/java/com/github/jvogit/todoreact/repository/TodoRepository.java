package com.github.jvogit.todoreact.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entities.todos.Todo;
import com.github.jvogit.todoreact.entities.todos.TodoKey;

@Repository
public interface TodoRepository extends CrudRepository<Todo, TodoKey> {
    
}
