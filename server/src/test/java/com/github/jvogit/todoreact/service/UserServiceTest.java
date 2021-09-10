package com.github.jvogit.todoreact.service;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.jvogit.todoreact.config.jwt.JwtConfigProperties;
import com.github.jvogit.todoreact.model.JwtUserDetails;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.github.jvogit.todoreact.util.TestUtil.TEST_ACCESS_TOKEN_ALGO;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_ACCESS_TOKEN_VERIFIER;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_EMAIL;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_ID;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_ISSUER;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_PASSWORD;
import static com.github.jvogit.todoreact.util.TestUtil.TEST_USERNAME;
import static com.github.jvogit.todoreact.util.TestUtil.generateMockAccessToken;
import static com.github.jvogit.todoreact.util.TestUtil.mockUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final Algorithm algo = TEST_ACCESS_TOKEN_ALGO;

    private final JWTVerifier jwtVerifier = TEST_ACCESS_TOKEN_VERIFIER;

    @Mock
    private JwtConfigProperties jwtConfigProperties;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository,
                passwordEncoder,
                TEST_ACCESS_TOKEN_ALGO,
                TEST_ACCESS_TOKEN_VERIFIER,
                jwtConfigProperties
        );
    }

    @Test
    void createUser_happy() {
        final User mockRequestUser = User.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        final User mockUser = mockUser();

        when(passwordEncoder.encode(any())).thenReturn(TEST_PASSWORD);
        when(userRepository.save(mockRequestUser)).thenReturn(mockUser);

        final User actualUser = userService.createUser(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD);

        assertThat(actualUser, is(mockUser));
    }

    @Test
    void getUser_happy() {
        final User mockUser = mockUser();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(mockUser));

        final Optional<User> actual = userService.getUser(TEST_ID);

        assertThat(actual, is(Optional.of(mockUser)));
    }

    @ParameterizedTest
    @ValueSource(strings = { TEST_USERNAME, TEST_EMAIL })
    void getUserByUsernameOrEmail_happy(final String usernameOrEmail) {
        final User mockUser = mockUser();

        when(userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.of(mockUser));

        final Optional<User> actual = userService.getUserByUsernameOrEmail(usernameOrEmail);

        assertThat(actual, is(Optional.of(mockUser)));
    }

    @Test
    void getUserDetailsFromToken_happy() {
        final JwtUserDetails expectedUserDetails = JwtUserDetails.builder()
                .id(TEST_ID)
                .username(TEST_USERNAME)
                .build();

        final Optional<JwtUserDetails> actual = userService.getUserDetailsFromToken(generateMockAccessToken());

        assertThat(actual, is(Optional.of(expectedUserDetails)));
    }

    @Test
    void getUserDetailsFromToken_invalid() {
        final Optional<JwtUserDetails> actual = userService.getUserDetailsFromToken("invalid token");

        assertThat(actual, is(Optional.empty()));
    }

    @Test
    void generateAccessToken_happy() {
        final User mockUser = mockUser();
        when(jwtConfigProperties.getIssuer()).thenReturn(TEST_ISSUER);

        final String actualToken = userService.generateAccessToken(mockUser);
        final DecodedJWT decodedJWT = TEST_ACCESS_TOKEN_VERIFIER.verify(actualToken);
        final Instant createdAt = decodedJWT.getIssuedAt().toInstant();
        final Instant expiresAt = decodedJWT.getExpiresAt().toInstant();
        final Duration duration = Duration.between(createdAt, expiresAt);

        assertThat(decodedJWT.getIssuer(), is(TEST_ISSUER));
        assertThat(decodedJWT.getSubject(), is(mockUser.getUsername()));
        assertThat(decodedJWT.getClaim("userId").asString(), is(mockUser.getId().toString()));
        assertThat(duration.getSeconds(), is(TimeUnit.MINUTES.toSeconds(15)));
    }
}
