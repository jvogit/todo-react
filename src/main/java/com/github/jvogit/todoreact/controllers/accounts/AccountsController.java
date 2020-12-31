package com.github.jvogit.todoreact.controllers.accounts;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jvogit.todoreact.jwt.JwtUserPrincipal;
import com.github.jvogit.todoreact.payloads.LoginPayload;
import com.github.jvogit.todoreact.payloads.SignupPayload;
import com.github.jvogit.todoreact.responses.ApiResponse;
import com.github.jvogit.todoreact.responses.ApiSuccess;
import com.github.jvogit.todoreact.services.UserService;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ApiResponse signup(@Valid @RequestBody SignupPayload payload) {
        userService.createUser(payload);

        return new ApiSuccess();
    }

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginPayload payload) {
        return userService.authenticate(payload);
    }

    @GetMapping("/me")
    public ApiResponse me(@AuthenticationPrincipal JwtUserPrincipal _user) {
        return new ApiSuccess() {
            @JsonProperty
            private Object user = _user;
        };
    }

}
