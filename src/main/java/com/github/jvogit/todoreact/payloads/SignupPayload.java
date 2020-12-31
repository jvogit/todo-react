package com.github.jvogit.todoreact.payloads;

import lombok.Data;

@Data
public class SignupPayload {
    private final String username;
    private final String email;
    private final String password;
}
