-- Limpa os dados das tabelas antes de inserir novos registros
DELETE FROM answer_options;
DELETE FROM tasks;
DELETE FROM courses;
DELETE FROM users;

-- Insere um novo usuário (instrutor)
INSERT INTO users (id, created_at, name, email, role, password)
VALUES (1, CURRENT_TIMESTAMP, 'Instructor', 'instructor@alura.com', 'INSTRUCTOR', 'password123');

-- Insere um novo curso associado ao instrutor
INSERT INTO courses (id, created_at, title, description, instructor_id, status)
VALUES (1, CURRENT_TIMESTAMP, 'Java Basics', 'Learn the basics of Java programming.', 1, 'BUILDING');
