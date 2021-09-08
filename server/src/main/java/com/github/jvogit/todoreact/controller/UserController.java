package com.github.jvogit.todoreact.controller;

import com.github.jvogit.todoreact.model.JwtUserDetails;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.model.input.LoginInput;
import com.github.jvogit.todoreact.model.input.RegisterInput;
import com.github.jvogit.todoreact.model.response.LoginResponse;
import com.github.jvogit.todoreact.model.response.RegisterResponse;
import com.github.jvogit.todoreact.service.RefreshTokenService;
import com.github.jvogit.todoreact.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlController;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private HttpServletResponse response;

    @Autowired
    public UserController(
            final UserService userService,
            final RefreshTokenService refreshTokenService,
            final AuthenticationManager authenticationManager,
            final HttpServletResponse response) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.response = response;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public User me() {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.getUser(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Can't find user."));
    }

    @MutationMapping
    public RegisterResponse register(@Argument("input") final RegisterInput input) {
        final User user = userService.createUser(input.username(), input.email(), input.password());
        log.info("Created {}", user);

        return new RegisterResponse(true);
    }

    @MutationMapping
    public LoginResponse login(@Argument("input") final LoginInput input) {
        log.info("Logging in {}", input.usernameOrEmail());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.usernameOrEmail(), input.password())
            );

            final JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
            final User user = userService.getUserByUsernameOrEmail(userDetails.getUsername()).get();
            final String accessToken = userService.generateAccessToken(user);
            final String refreshToken = refreshTokenService.generateRefreshToken(user);

            // set jid cookie
            refreshTokenService.setRefreshTokenCookie(response, refreshToken);

            return new LoginResponse(
                    true,
                    accessToken,
                    user
            );
        } catch (final BadCredentialsException ex) {
            log.error("Bad credentials");
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @MutationMapping
    public void logout() {
        refreshTokenService.setRefreshTokenCookie(response, null);
    }
}
