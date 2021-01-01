package com.github.jvogit.todoreact.entities.todos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.jvogit.todoreact.entities.audits.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todo_items")
@NoArgsConstructor
@Getter
@Setter
public class TodoItem extends DateAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private String text;
    
    private Boolean completed;
    
    @Column(name = "todo_index")
    private Integer todoIndex;
    
    @ManyToOne
    @JsonBackReference
    private Todo todo;
    
    public TodoItem (Todo todo, String text, Boolean completed) {
        this.todo = todo;
        this.text = text;
        this.completed = completed;
    }
    
    public TodoItem (String text, Boolean completed) {
        this.text = text;
        this.completed = completed;
    }
    
    @PrePersist
    @PreUpdate
    private void updateIndex() {
        todoIndex = todo.getTodoItems().indexOf(this);
    }
    
}