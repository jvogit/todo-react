package com.github.jvogit.todoreact.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entities.todos.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    
    Optional<Todo> findByUserIdAndDate(Long user_id, LocalDate date);
    
}
