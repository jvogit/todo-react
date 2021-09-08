package com.github.jvogit.todoreact.controller;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.User;
import com.github.jvogit.todoreact.service.TodoService;
import com.github.jvogit.todoreact.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.jvogit.todoreact.util.TestUtil.TEST_ID;
import static com.github.jvogit.todoreact.util.TestUtil.mockAuthenticationPrincipal;
import static com.github.jvogit.todoreact.util.TestUtil.mockTodoFor;
import static com.github.jvogit.todoreact.util.TestUtil.mockUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private User expectedUser;

    private List<Todo> expectedTodos;

    private Todo expectedFirstTodo;

    @BeforeEach
    void setUp() {
        mockAuthenticationPrincipal();

        expectedUser = mockUser();

        expectedTodos = Collections.singletonList(mockTodoFor(expectedUser, UUID.randomUUID(), "This is a test!"));

        expectedFirstTodo = expectedTodos.get(0);
    }

    @Test
    void todos_happy() {
        when(userService.getUser(TEST_ID)).thenReturn(Optional.of(expectedUser));

        final List<Todo> todo = todoController.todos();

        assertThat(todo, is(expectedTodos));
    }

    @Test
    void createTodo_happy() {
        final String expectedItem = "Mock todo";

        when(userService.getUser(TEST_ID)).thenReturn(Optional.of(expectedUser));
        when(todoService.createTodo(eq(expectedUser), any(), eq(expectedItem))).thenReturn(expectedFirstTodo);

        final Todo todo = todoController.createTodo(expectedItem);

        assertThat(todo, is(expectedFirstTodo));
    }

    @Test
    void changeCompleted_happy() {
        final Todo newExpected = Todo.builder()
                .id(expectedFirstTodo.getId())
                .item(expectedFirstTodo.getItem())
                .completed(!expectedFirstTodo.isCompleted())
                .build();

        final Todo actual = todoController.changeCompleted(newExpected);

        verify(todoService, times(1))
                .updateTodo(eq(newExpected.getId().toString()), eq(newExpected.getItem()), eq(newExpected.isCompleted()));
        assertThat(actual, is(newExpected));
    }

    @Test
    void changeItem_happy() {
        final Todo newExpected = Todo.builder()
                .id(expectedFirstTodo.getId())
                .item(expectedFirstTodo.getItem() + "NEW")
                .completed(expectedFirstTodo.isCompleted())
                .build();

        final Todo actual = todoController.changeItem(newExpected);

        verify(todoService, times(1))
                .updateTodo(eq(newExpected.getId().toString()), eq(newExpected.getItem()), newExpected.isCompleted());
        assertThat(actual, is(newExpected));
    }
}
