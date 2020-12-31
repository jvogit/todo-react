package com.github.jvogit.todoreact.responses.accounts;

import com.github.jvogit.todoreact.responses.ApiSuccess;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends ApiSuccess {
    private final Object user;
    private final String token;
}
