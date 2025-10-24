CREATE TABLE alumnos (
    id BIGSERIAL PRIMARY KEY,
    nombres VARCHAR(120) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    dni VARCHAR(15) UNIQUE,
    contacto VARCHAR(120),
    aula_id BIGINT REFERENCES aulas (id)
);

CREATE INDEX alumnos_aula_id_idx ON alumnos (aula_id);

CREATE TABLE matriculas (
    id BIGSERIAL PRIMARY KEY,
    alumno_id BIGINT NOT NULL REFERENCES alumnos (id) ON DELETE CASCADE,
    aula_id BIGINT NOT NULL REFERENCES aulas (id),
    anio INTEGER NOT NULL,
    estado VARCHAR(30),
    CONSTRAINT matriculas_unicas UNIQUE (alumno_id, aula_id, anio)
);

CREATE TABLE asistencias (
    id BIGSERIAL PRIMARY KEY,
    alumno_id BIGINT NOT NULL REFERENCES alumnos (id) ON DELETE CASCADE,
    fecha DATE NOT NULL,
    presente BOOLEAN NOT NULL,
    CONSTRAINT asistencias_unicas UNIQUE (alumno_id, fecha)
);

CREATE TABLE notas (
    id BIGSERIAL PRIMARY KEY,
    alumno_id BIGINT NOT NULL REFERENCES alumnos (id) ON DELETE CASCADE,
    curso VARCHAR(80),
    periodo VARCHAR(20),
    valor DOUBLE PRECISION NOT NULL
);

CREATE INDEX notas_alumno_id_idx ON notas (alumno_id);
