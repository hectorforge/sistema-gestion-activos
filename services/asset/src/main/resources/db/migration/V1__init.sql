CREATE TABLE IF NOT EXISTS tbl_categoria (
    id_category UUID PRIMARY KEY NOT NULL,
    nombre_categoria VARCHAR(255) NOT NULL,
    abreviatura_categoria VARCHAR(50) NOT NULL,
    descripcion_categoria VARCHAR(255),
    url_img_categoria VARCHAR(255),
    es_activo BOOLEAN NOT NULL DEFAULT TRUE,
    esta_eliminado BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS tbl_activo (
    id_activo UUID PRIMARY KEY NOT NULL,
    codigo_inventario VARCHAR(255) NOT NULL,
    nombre_activo VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    url_img_activo VARCHAR(255),

    categoria_id UUID NOT NULL,

    estado_actual VARCHAR(255) NOT NULL,
    ubicacion_fisica VARCHAR(255) NOT NULL,
    fecha_ingreso DATE,

    valor_referencial NUMERIC(38,2),
    proveedor VARCHAR(255),
    vida_util_meses INTEGER,
    observaciones VARCHAR(255),

    es_activo BOOLEAN NOT NULL DEFAULT TRUE,
    esta_eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP,

    CONSTRAINT uc_codigo_inventario UNIQUE (codigo_inventario),

    CONSTRAINT fk_activo_categoria
    FOREIGN KEY (categoria_id)
    REFERENCES tbl_categoria(id_category)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
    );

CREATE TABLE IF NOT EXISTS tbl_asignacion (
    id_asignacion UUID PRIMARY KEY NOT NULL,
    id_activo UUID NOT NULL,
    id_usuario UUID NOT NULL,
    fecha_asignacion DATE NOT NULL,
    fecha_devolucion DATE,
    estado_asignacion VARCHAR(255) NOT NULL,
    observaciones VARCHAR(255)
    );

-- =========================================================
-- INDICES tbl_categoria
-- =========================================================
CREATE INDEX IF NOT EXISTS idx_categoria_nombre
    ON tbl_categoria(nombre_categoria);

CREATE INDEX IF NOT EXISTS idx_categoria_es_activo
    ON tbl_categoria(es_activo);

-- =========================================================
-- INDICES tbl_activo
-- =========================================================
CREATE INDEX IF NOT EXISTS idx_activo_nombre
    ON tbl_activo(nombre_activo);

CREATE INDEX IF NOT EXISTS idx_activo_es_activo
    ON tbl_activo(es_activo);

CREATE INDEX IF NOT EXISTS idx_activo_codigo
    ON tbl_activo(codigo_inventario);

CREATE INDEX IF NOT EXISTS idx_activo_categoria
    ON tbl_activo(categoria_id);

CREATE INDEX IF NOT EXISTS idx_activo_estado_eliminado
    ON tbl_activo(estado_actual, esta_eliminado);

-- =========================================================
-- INDICES tbl_asignacion
-- =========================================================

CREATE INDEX IF NOT EXISTS idx_asignacion_activo
    ON tbl_asignacion(id_activo);

CREATE INDEX IF NOT EXISTS idx_asignacion_usuario
    ON tbl_asignacion(id_usuario);

CREATE INDEX IF NOT EXISTS idx_asignacion_estado
    ON tbl_asignacion(estado_asignacion);
