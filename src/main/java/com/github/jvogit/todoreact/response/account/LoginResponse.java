package com.github.jvogit.todoreact.response.account;

import com.github.jvogit.todoreact.response.ApiSuccess;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends ApiSuccess {
    private final Object user;
    private final String token;
}
