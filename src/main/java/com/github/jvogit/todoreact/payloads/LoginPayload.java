package com.github.jvogit.todoreact.payloads;

import lombok.Data;

@Data
public class LoginPayload {
    private final String username;
    private final String password;
}
