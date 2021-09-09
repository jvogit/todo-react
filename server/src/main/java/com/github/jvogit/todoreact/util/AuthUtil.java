package com.github.jvogit.todoreact.util;

import com.github.jvogit.todoreact.model.JwtUserDetails;
import com.github.jvogit.todoreact.model.Todo;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class AuthUtil {
    private AuthUtil() {
    }

    public static boolean authorize(final Todo todo, final UUID userId) {
        return userId.equals(todo.getUser().getId());
    }

    public static JwtUserDetails getUserDetails() {
        return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
