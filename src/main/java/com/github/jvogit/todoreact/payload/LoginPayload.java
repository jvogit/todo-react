package com.github.jvogit.todoreact.payload;

import lombok.Data;

@Data
public class LoginPayload {
    private final String username;
    private final String password;
}
