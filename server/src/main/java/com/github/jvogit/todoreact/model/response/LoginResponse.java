package com.github.jvogit.todoreact.model.response;

import com.github.jvogit.todoreact.model.User;

public record LoginResponse(boolean success, String token, User user) {
}
