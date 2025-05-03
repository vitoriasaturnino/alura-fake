CREATE TABLE tasks (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    course_id bigint(20) NOT NULL,
    statement varchar(255) NOT NULL,
    task_order int NOT NULL,
    task_type enum('OPEN_TEXT', 'SINGLE_CHOICE', 'MULTIPLE_CHOICE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT unique_task_statement UNIQUE (course_id, statement),
    CONSTRAINT unique_task_order UNIQUE (course_id, task_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;