# Architecture Patterns Examples

This repository demonstrates the same `Todo` use case in separate Git branches.

## Branch map

- `main` -> Layered Architecture (controller -> service -> repository)
- `hexagonal-architecture` -> Hexagonal Architecture (ports and adapters)
- `onion-architecture` -> Onion Architecture (domain core with outward dependencies)
- `clean-architecture` -> Clean Architecture (use cases and interface adapters)

## Current branch

`onion-architecture` contains the Onion Architecture example:

- Endpoint: `POST /onion/todos`
- Endpoint: `GET /onion/todos`
- Package root: `com.awwad.example.architecture.onion`
- Layers (innermost → outermost): `domain` → `application` → `infrastructure` + `presentation`

## Run and test

Use Maven Wrapper from project root.

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## Create/switch branches

```powershell
git switch main
git switch hexagonal-architecture
git switch onion-architecture
git switch clean-architecture
```

