CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE todos
(
    id         UUID NOT NULL,
    created_at TIMESTAMP with time zone,
    updated_at TIMESTAMP with time zone,
    completed  BOOLEAN,
    item       VARCHAR(255),
    pos        INTEGER,
    user_id    UUID,
    CONSTRAINT pk_todos PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            UUID NOT NULL,
    created_at    TIMESTAMP with time zone,
    updated_at    TIMESTAMP with time zone,
    username      VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    token_version INTEGER,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE todos
    ADD CONSTRAINT FK_TODOS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);