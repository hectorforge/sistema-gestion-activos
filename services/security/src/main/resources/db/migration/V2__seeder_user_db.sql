INSERT INTO tbl_usuarios
(id, nombre, apellido, email, foto_de_perfil, password, es_activo, esta_eliminado, rol)
VALUES
-- ADMINISTRADOR : credenciales ( admin@gmail.com, admin123 )
('9f3c2a6e-8b1d-4f7a-9c2e-6d5b1a3e7f90', 'admin', 'principal', 'adminx@gmail.com', NULL,
 '$2a$10$2uQTSngzATAOQPERXF9nuO3qbJ6ruZ5kIIFd6S/TwLNTsgpqflcp6', TRUE, FALSE, 'ADMINISTRADOR'),

-- TRABAJADOR : credenciales ( jhon@gmail.com, 123456 )
('c1a7d9b2-5e34-4a8f-b1c9-2f6e3d7a4b11', 'jhon', 'doe', 'jhondoe@gmail.com', NULL,
 '$2a$10$IG4JCn5RYQyBEojqYX0b3.JTzDFLEV9mgi0YUzAzBzaGOotiOujhG', TRUE, FALSE, 'TRABAJADOR'),

-- USUARIO : credenciales ( isagar@gmail.com , user123 )
('7b2e1c44-3f6a-4d91-8a57-9e2c5b1f0d33', 'isabel', 'garcia', 'isagar@gmail.com', NULL,
 '$2a$10$5QWZoWNJoRY1n8DwbUa/s.4XJh5suFhTmb.7kAz3S0UPSAsacgYWO', TRUE, FALSE, 'USUARIO');
