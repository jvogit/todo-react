package com.github.jvogit.todoreact.entities.todos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoKey implements Serializable {
    private Long user_id;
    private LocalDate date;
}
