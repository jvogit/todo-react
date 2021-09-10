package com.github.jvogit.todoreact.model.input;

import java.util.UUID;

public record TodoInput(UUID id, boolean completed, String item) {
}
