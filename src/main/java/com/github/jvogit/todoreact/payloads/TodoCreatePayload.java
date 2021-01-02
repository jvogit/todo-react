package com.github.jvogit.todoreact.payloads;

import java.util.List;

import lombok.Data;

@Data
public class TodoCreatePayload {
    private final String date;
    private final List<TodoCreateItem> items;
    
    @Data
    public static class TodoCreateItem {
        private Long id;
        private Boolean completed;
        private String text;
    }
}
