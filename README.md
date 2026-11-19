# Playgrounds-Microservices
Web application for reserving sports playgrounds and sports services. 
By this web application you can find people to play with, playground location, create competitions, 
purchase sports products from playground store and more planed modules will be added to it. 

This repository is a microservices Architecture version of the already developed Playgrounds repository which is built using SOA Architecture.

Technology stack used in this web application:
- Microservices Architecture
- API-based communication (API Composition pattern)
- Spring Boot
- Spring REST
- Spring Cloud
- Spring WebFlux
- Service registration and discovery pattern using Eureka server
- Circuit Breaker pattern using Resilience4j
- Caching using Redis in-memory DB
- Spring Data
- Hibernate/JPA
- Mysql Database
- Maven
- Docker

## Services

| Service | Responsibility |
| --- | --- |
| `discovery-server` | Eureka service registry |
| `playground-service` | Playground catalogue and search ([API docs](playground-service/README.md)) |
| `reservation-service` | Playground reservations |
| `user-service` | User accounts |
| `team-service` | Teams and memberships |
| `invitation-service` | Team invitations |
| `store-service` | Sports store catalogue |
| `photo-service` | Photo storage |

## Building a service

Each service is an independent Maven project. To build and test one of them:

```bash
cd playground-service
mvn test
```

The tests for `playground-service` run on an in-memory H2 database, so MySQL
and Redis do not need to be running.