package com.github.jvogit.todoreact.util;

import com.github.jvogit.todoreact.model.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthUtil {
    private AuthUtil() {
    }

    public static JwtUserDetails getUserDetails() {
        return (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
