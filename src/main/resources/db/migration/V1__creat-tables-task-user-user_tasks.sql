CREATE TABLE task
(
    id            INT NOT NULL,
    name          VARCHAR(255) NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    `description` VARCHAR(255) NULL,
    timestamp     datetime NULL,
    updated       datetime NULL,
    user_id       INT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         INT NOT NULL,
    name       VARCHAR(255) NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    status     SMALLINT NULL,
    `role`     SMALLINT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_tasks
(
    user_id  INT NOT NULL,
    tasks_id INT NOT NULL
);

ALTER TABLE user_tasks
    ADD CONSTRAINT uc_user_tasks_tasks UNIQUE (tasks_id);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_tasks
    ADD CONSTRAINT fk_usetas_on_task FOREIGN KEY (tasks_id) REFERENCES task (id);

ALTER TABLE user_tasks
    ADD CONSTRAINT fk_usetas_on_user FOREIGN KEY (user_id) REFERENCES user (id);