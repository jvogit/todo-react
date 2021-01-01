package com.github.jvogit.todoreact.entities.todos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.jvogit.todoreact.entities.accounts.User;
import com.github.jvogit.todoreact.entities.audits.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "todos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "user_id", "date" })
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Todo extends DateAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    
    @OneToMany(mappedBy = "todo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "todo_index")
    @JsonManagedReference
    private List<TodoItem> todoItems;
    
    public Todo(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        this.todoItems = new ArrayList<>();
    }
    
    public void addItem(TodoItem item) {
        this.todoItems.add(item);
        item.setTodo(this);
    }
    
}
