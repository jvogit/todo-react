package com.github.jvogit.todoreact.entities.todos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.jvogit.todoreact.entities.accounts.User;
import com.github.jvogit.todoreact.entities.audits.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "todos"
)
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class Todo extends DateAudit {
    @EmbeddedId
    @JsonUnwrapped
    private TodoKey id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonManagedReference
    private User user;
    
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "index")
    @JsonManagedReference
    private List<TodoItem> items;
    
    public Todo(User user, LocalDate date) {
        this.user = user;
        this.id = new TodoKey(user.getId(), date);
        this.items = new ArrayList<>();
    }
    
    public Todo(Long user_id, LocalDate date) {
        this.id = new TodoKey(user_id, date);
        this.items = new ArrayList<>();
    }
    
    public void setItems(List<TodoItem> items) {
        this.items = new ArrayList<>();
        items.forEach(this::addItem);
    }
    
    public void addItem(TodoItem item) {
        this.items.add(item);
        item.setTodo(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Todo))
            return false;
        Todo other = (Todo) obj;
        return Objects.equals(id, other.id);
    }
}
