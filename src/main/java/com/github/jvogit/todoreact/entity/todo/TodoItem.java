package com.github.jvogit.todoreact.entity.todo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.jvogit.todoreact.entity.audit.DateAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "todo_items"
)
@NoArgsConstructor
@Getter
@Setter
public class TodoItem extends DateAudit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String text;

    private Boolean completed;

    private Integer index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "date", referencedColumnName = "date")})
    @JsonBackReference
    private Todo todo;
    
    public TodoItem(String text, Boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    @PrePersist
    @PreUpdate
    private void updateIndex() {
        index = todo.getItems().indexOf(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + Objects.hash(completed, id, index, text);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof TodoItem))
            return false;
        TodoItem other = (TodoItem) obj;
        return Objects.equals(completed, other.completed)
                && Objects.equals(id, other.id)
                && Objects.equals(index, other.index)
                && Objects.equals(text, other.text);
    }

}
