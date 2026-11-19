# playground-service

Spring Boot service that owns playground records for the Playgrounds
microservices platform. It exposes a JSON API over a MySQL-backed JPA
repository and is registered with Eureka.

## API

Base path: `/api/playground`

| Method | Path | Description | Success |
| --- | --- | --- | --- |
| `GET` | `/` | List every playground | `200` |
| `GET` | `/search` | Filtered, paginated search | `200` |
| `GET` | `/{id}` | Fetch one playground | `200` |
| `POST` | `/` | Create a playground | `201` + `Location` |
| `PUT` | `/` | Update a playground (id in body) | `200` |
| `DELETE` | `/{id}` | Delete a playground | `200` |

### Search

```
GET /api/playground/search?area=downtown&name=arena&page=0&size=20
```

| Parameter | Default | Rules |
| --- | --- | --- |
| `area` | – | optional, case-insensitive "contains" match |
| `name` | – | optional, case-insensitive "contains" match |
| `page` | `0` | `>= 0` |
| `size` | `20` | `1..100` |

Response:

```json
{
  "content": [
    {
      "id": 1,
      "name": "Central Arena",
      "address": "1 Main Street",
      "description": "Indoor courts",
      "phone": "01000000000",
      "area": "Downtown",
      "availableTime": null,
      "pricePerHour": "150.00"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

### Validation

`POST` and `PUT` validate the request body and return `400` with field-level
details before touching the database:

| Field | Rule |
| --- | --- |
| `name` | required, max 120 characters |
| `address` | required, max 255 characters |
| `area` | required, max 120 characters |
| `description` | optional, max 2000 characters |
| `phone` | optional, 6–20 digits/spaces/`+()-` |
| `pricePerHour` | optional, non-negative amount with up to 2 decimals |

## Error model

Every failure uses the same envelope:

```json
{
  "timestamp": "2026-11-13T18:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Request validation failed",
  "path": "/api/playground/",
  "fieldErrors": [
    { "field": "name", "message": "name is required" }
  ]
}
```

| Status | Cause |
| --- | --- |
| `400` | validation failure, malformed JSON, missing id on update |
| `404` | no playground with the requested id |
| `405` | unsupported HTTP method |
| `500` | unexpected server error (details are logged, not returned) |

## Caching

The single-playground lookup is cached in Redis under `playgroundCache`.
Create, update and delete evict the whole cache. Set
`spring.cache.type=none` to disable caching (tests do this).

## Running

```bash
mvn spring-boot:run
```

Configuration lives in `src/main/resources/application.properties` and uses
the shared MySQL and Redis instances from the platform's Docker setup.

## Tests

```bash
mvn test
```

The suite runs against H2 in MySQL compatibility mode, with Redis caching and
Eureka disabled through `src/test/resources/application.properties`:

- `PlaygroundMapperTest` – entity/DTO mapping and trimming
- `PlaygroundServiceImplTest` – not-found handling, paging, cache-facing logic
- `PlaygroundControllerTest` – `@WebMvcTest` contract and validation tests
- `PlaygroundRepositoryImplTest` – `@DataJpaTest` search, count, update, delete
- `PlaygroundServiceApplicationTests` – context load
