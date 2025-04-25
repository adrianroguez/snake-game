-- Crear tabla de usuarios
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

-- Insertar datos de ejemplo
INSERT INTO users (user, email, password) VALUES ('Adrián', 'adrian@example.com', '1234');
INSERT INTO users (user, email, password) VALUES ('Laura', 'laura@example.com', 'abcd');
INSERT INTO users (user, email, password) VALUES ('Carlos', 'carlos@example.com', 'qwerty');

-- Mostrar todos los usuarios
SELECT * FROM users;
