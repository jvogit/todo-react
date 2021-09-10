package com.github.jvogit.todoreact.repository;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    Optional<Todo> findByIdAndUser(final UUID id, final User user);
    Optional<Todo> deleteByIdAndUser(final UUID id, final User user);
}
