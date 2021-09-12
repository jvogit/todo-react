package com.github.jvogit.todoreact.controller;

import com.github.jvogit.todoreact.model.Todo;
import com.github.jvogit.todoreact.model.input.TodoInput;
import com.github.jvogit.todoreact.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.github.jvogit.todoreact.util.TestUtil.TEST_USERID;
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
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private List<Todo> expectedTodos;

    private Todo expectedFirstTodo;

    @BeforeEach
    void setUp() {
        mockAuthenticationPrincipal();

        expectedTodos = Collections.singletonList(mockTodoFor(mockUser(), UUID.randomUUID(), "This is a test!"));

        expectedFirstTodo = expectedTodos.get(0);
    }

    @Test
    void todos_happy() {
        when(todoService.todos(TEST_USERID)).thenReturn(expectedTodos);

        final List<Todo> todo = todoController.todos();

        assertThat(todo, is(expectedTodos));
    }

    @Test
    void createTodo_happy() {
        final String expectedItem = "Mock todo";

        when(todoService.createTodo(any(), eq(TEST_USERID), eq(expectedItem))).thenReturn(expectedFirstTodo);

        final Todo todo = todoController.createTodo(expectedItem);

        assertThat(todo, is(expectedFirstTodo));
    }

    @Test
    void updateTodo_completed_happy() {
        final Todo newExpected = Todo.builder()
                .id(expectedFirstTodo.getId())
                .item(expectedFirstTodo.getItem())
                .completed(!expectedFirstTodo.isCompleted())
                .build();
        final TodoInput newExpectedInput = new TodoInput(
                newExpected.getId(), newExpected.isCompleted(), newExpected.getItem());

        when(todoService.updateTodo(newExpected.getId(), TEST_USERID, newExpected.getItem(), newExpected.isCompleted()))
                .thenReturn(newExpected);

        final Todo actual = todoController.updateTodo(newExpectedInput);

        verify(todoService, times(1))
                .updateTodo(eq(newExpected.getId()), eq(TEST_USERID), eq(newExpected.getItem()), eq(newExpected.isCompleted()));
        assertThat(actual, is(newExpected));
    }

    @Test
    void updateTodo_item_happy() {
        final Todo newExpected = Todo.builder()
                .id(expectedFirstTodo.getId())
                .item(expectedFirstTodo.getItem() + "NEW")
                .completed(expectedFirstTodo.isCompleted())
                .build();
        final TodoInput newExpectedInput = new TodoInput(
                newExpected.getId(), newExpected.isCompleted(), newExpected.getItem());

        when(todoService.updateTodo(newExpected.getId(), TEST_USERID, newExpected.getItem(), newExpected.isCompleted()))
                .thenReturn(newExpected);

        final Todo actual = todoController.updateTodo(newExpectedInput);

        verify(todoService, times(1))
                .updateTodo(eq(newExpected.getId()), eq(TEST_USERID), eq(newExpected.getItem()), eq(newExpected.isCompleted()));
        assertThat(actual, is(newExpected));
    }

    @Test
    void deleteTodo_happy() {
        todoController.deleteTodo(expectedFirstTodo.getId());

        verify(todoService, times(1)).deleteTodo(expectedFirstTodo.getId(), TEST_USERID);
    }
}
