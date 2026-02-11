CREATE TABLE IF NOT EXISTS tbl_usuarios (
    id UUID PRIMARY KEY NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    foto_de_perfil VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    es_activo BOOLEAN NOT NULL DEFAULT TRUE,
    esta_eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    rol VARCHAR(50) NOT NULL
    );

-- =========================================================
-- INDICES tbl_usuarios
-- =========================================================
CREATE INDEX IF NOT EXISTS idx_usuario_nombre
    ON tbl_usuarios(nombre);

CREATE INDEX IF NOT EXISTS idx_usuario_email
    ON tbl_usuarios(email);

CREATE INDEX IF NOT EXISTS idx_usuario_rol
    ON tbl_usuarios(rol);
