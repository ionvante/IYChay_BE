CREATE TABLE reportes_ia (
    id BIGSERIAL PRIMARY KEY,
    scope VARCHAR(20) NOT NULL,
    ref_id BIGINT,
    periodo VARCHAR(20),
    texto TEXT,
    json TEXT,
    pdf_url VARCHAR(255),
    creado_en TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT reportes_ia_scope_check CHECK (scope IN ('alumno', 'aula', 'colegio'))
);

CREATE INDEX reportes_ia_scope_idx ON reportes_ia (scope);
