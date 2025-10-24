CREATE TABLE conversaciones (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    ref_id BIGINT,
    CONSTRAINT conversaciones_tipo_check CHECK (tipo IN ('AULA', 'ALUMNO'))
);

CREATE TABLE conversacion_participantes (
    conversacion_id BIGINT NOT NULL REFERENCES conversaciones (id) ON DELETE CASCADE,
    usuario_id BIGINT NOT NULL REFERENCES usuarios (id) ON DELETE CASCADE,
    PRIMARY KEY (conversacion_id, usuario_id)
);

CREATE TABLE mensajes (
    id BIGSERIAL PRIMARY KEY,
    conversacion_id BIGINT NOT NULL REFERENCES conversaciones (id) ON DELETE CASCADE,
    emisor_usuario_id BIGINT NOT NULL REFERENCES usuarios (id),
    texto TEXT NOT NULL,
    adjunto_url VARCHAR(255),
    fecha TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX mensajes_conversacion_id_idx ON mensajes (conversacion_id);
