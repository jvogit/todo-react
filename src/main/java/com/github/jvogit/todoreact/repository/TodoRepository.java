package com.github.jvogit.todoreact.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entity.todo.Todo;
import com.github.jvogit.todoreact.entity.todo.TodoKey;

@Repository
public interface TodoRepository extends CrudRepository<Todo, TodoKey> {
    
}
