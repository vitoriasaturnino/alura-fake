CREATE TABLE answer_options (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    task_id bigint(20) NOT NULL,
    option_text varchar(80) NOT NULL,
    is_correct boolean NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_AnswerOption_Task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT unique_option_text UNIQUE (task_id, option_text)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;