package com.github.jvogit.todoreact.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entities.roles.Role;
import com.github.jvogit.todoreact.entities.roles.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Optional<Role> findByName(RoleName roleName);
}
