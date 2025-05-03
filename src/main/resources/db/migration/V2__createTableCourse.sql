CREATE TABLE courses (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    title varchar(100) NOT NULL,
    description text NOT NULL,
    instructor_id bigint(20) NOT NULL,
    status enum('BUILDING', 'PUBLISHED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'BUILDING',
    published_at datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Author FOREIGN KEY (instructor_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;