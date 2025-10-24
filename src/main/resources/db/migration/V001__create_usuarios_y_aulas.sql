CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT usuarios_rol_check CHECK (rol IN ('ADMIN', 'PROFESOR', 'PADRE'))
);

CREATE TABLE aulas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    tutor_usuario_id BIGINT REFERENCES usuarios (id),
    horario VARCHAR(120),
    capacidad INTEGER,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    CONSTRAINT aulas_estado_check CHECK (estado IN ('ACTIVA', 'INACTIVA'))
);

CREATE INDEX aulas_tutor_usuario_id_idx ON aulas (tutor_usuario_id);
