package com.github.jvogit.todoreact.model;

import reactor.util.annotation.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "todos")
public class Todo extends DateAudit {

    @Id
    private UUID id;

    @Column
    @NotNull
    private boolean completed;

    @Column
    @Size(max = 255)
    private String item;

    @Column
    private Integer pos;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Todo(final UUID id, final boolean completed, final String item, final Integer pos, final User user) {
        this.id = id;
        this.completed = completed;
        this.item = item;
        this.pos = pos;
        this.user = user;
    }

    public Todo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                ", completed=" + completed +
                ", item='" + item + '\'' +
                ", pos=" + pos +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return isCompleted() == todo.isCompleted() && Objects.equals(getId(), todo.getId()) && Objects.equals(getItem(), todo.getItem()) && Objects.equals(getPos(), todo.getPos()) && Objects.equals(getUser(), todo.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isCompleted(), getItem(), getPos(), getUser());
    }

    public static Todo.Builder builder() {
        return new Todo.Builder();
    }

    public static class Builder {

        private UUID id;
        private boolean completed;
        private String item;
        private Integer pos;
        private User user;

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder completed(final boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder item(final String item) {
            this.item = item;
            return this;
        }

        public Builder pos(final Integer pos) {
            this.pos = pos;
            return this;
        }

        public Builder user(final User user) {
            this.user = user;
            return this;
        }

        public Todo build() {
            return new Todo(id, completed, item, pos, user);
        }
    }
}
