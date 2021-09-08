package com.github.jvogit.todoreact.service;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.repository.TodoRepository;
import com.github.jvogit.todoreact.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.github.jvogit.todoreact.util.TestUtil.mockTodoFor;
import static com.github.jvogit.todoreact.util.TestUtil.mockUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private User expectedUser;

    private Todo expectedTodo;

    private static final UUID EXPECTED_UUID = UUID.randomUUID();

    private static final String EXPECTED_ITEM = "This is a test";

    @BeforeEach
    void setUp() {
        expectedUser = mockUser();
        expectedTodo = mockTodoFor(expectedUser, EXPECTED_UUID, EXPECTED_ITEM);
    }

    @Test
    void createTodo_happy() {
        final Todo actual = todoService.createTodo(expectedUser, EXPECTED_UUID, EXPECTED_ITEM);

        verify(userRepository, times(1)).save(expectedUser);
        assertThat(actual, is(expectedTodo));
    }

    @Test
    void updateTodo_happy() {
        final String EXPECTED_NEW_ITEM = EXPECTED_ITEM + "NEW";
        final Todo newExpectedTodo = Todo.builder()
                .id(EXPECTED_UUID)
                .item(EXPECTED_NEW_ITEM)
                .user(expectedUser)
                .completed(true)
                .build();

        when(todoRepository.findById(EXPECTED_UUID)).thenReturn(Optional.of(expectedTodo));
        when(todoRepository.save(expectedTodo)).thenReturn(newExpectedTodo);

        final Todo actual = todoService.updateTodo(EXPECTED_UUID.toString(), EXPECTED_NEW_ITEM, true);

        assertThat(actual, is(newExpectedTodo));
    }
}
