package com.github.jvogit.springreactnextjs.model.response;

import com.github.jvogit.springreactnextjs.model.User;

public record LoginResponse(boolean success, String token, User user) {
}
