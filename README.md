# IYChay Backend (MVP)

Backend en Spring Boot 3.3 para la plataforma IYChay. Este repositorio contiene el esqueleto inicial para desarrollar el MVP con los módulos de autenticación, aulas, alumnos, mensajería y reportes IA.

## Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL 14+ (local o gestionado)

## Variables de entorno clave

| Variable | Descripción |
| --- | --- |
| `DB_URL` | Cadena JDBC de la base de datos |
| `DB_USER` | Usuario de conexión |
| `DB_PASS` | Contraseña |
| `JWT_SECRET` | Secreto para firmar JWT |
| `JWT_EXP_MINUTES` | Minutos de expiración de los tokens |
| `IA_BASE_URL` | Base URL del microservicio de IA |
| `IA_API_KEY` | API key para el microservicio de IA (opcional) |
| `STORAGE_BUCKET_URL` | Bucket/endpoint para PDFs |

## Ejecución local

```bash
mvn spring-boot:run
```

La aplicación escucha por defecto en `http://localhost:8080`.

## Estructura principal

- `com.iychay.be.auth`: autenticación y control de acceso.
- `com.iychay.be.classroom`: gestión de aulas.
- `com.iychay.be.student`: gestión de alumnos, matrículas, asistencias y notas.
- `com.iychay.be.message`: mensajería básica.
- `com.iychay.be.report`: generación y descarga de reportes IA.
- `com.iychay.be.common`: configuración compartida, manejo de errores y documentación.

Los diagramas C4 en texto se encuentran en `docs/diagrams/c4.txt`.

## Próximos pasos sugeridos

1. Implementar seguridad JWT completa (filtros, generación y validación de tokens).
2. Añadir repositorios y servicios complementarios para asistencia, notas y matrículas.
3. Integrar almacenamiento de PDF (S3/Cloud Storage) en el flujo de reportes IA.
4. Configurar pipelines CI/CD (GitHub Actions) y despliegues en el proveedor elegido.
5. Añadir migraciones Flyway y datos de ejemplo para entornos de desarrollo.
