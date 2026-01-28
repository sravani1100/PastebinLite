
# Pastebin Lite Backend

Spring Boot 3.2.1 REST API for Pastebin Lite.

## Quick Start

```bash
# Set environment variables
export DATABASE_URL=jdbc:postgresql://localhost:5432/pastebin
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

# Run
./mvnw spring-boot:run
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/pastebin` |
| `DATABASE_USERNAME` | Database username | `postgres` |
| `DATABASE_PASSWORD` | Database password | `postgres` |
| `PORT` | Server port | `8080` |
| `CORS_ORIGINS` | Allowed CORS origins | `http://localhost:4200` |

## API Documentation

See main [README](../README.md) for API endpoints.

# pastebin-lite
62bb2e4600b5286427d04623612787e4266c0773
