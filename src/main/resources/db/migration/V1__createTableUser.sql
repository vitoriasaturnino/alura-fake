CREATE TABLE users (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name varchar(100) NOT NULL,
    email varchar(255) NOT NULL,
    role enum('STUDENT', 'INSTRUCTOR') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'STUDENT',
    password varchar(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UC_Email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;