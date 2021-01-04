package com.github.jvogit.todoreact.payload;

import lombok.Data;

@Data
public class SignupPayload {
    private final String username;
    private final String email;
    private final String password;
}
