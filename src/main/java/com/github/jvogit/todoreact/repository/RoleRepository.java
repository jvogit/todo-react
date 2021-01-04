package com.github.jvogit.todoreact.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.jvogit.todoreact.entity.role.Role;
import com.github.jvogit.todoreact.entity.role.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Optional<Role> findByName(RoleName roleName);
}
