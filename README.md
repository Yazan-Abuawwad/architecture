# Architecture Patterns Examples

This repository demonstrates the same `Todo` use case in separate Git branches.

## Branch map

- `main` -> Layered Architecture (controller -> service -> repository)
- `hexagonal-architecture` -> Hexagonal Architecture (ports and adapters)
- `onion-architecture` -> Onion Architecture (domain core with outward dependencies)
- `clean-architecture` -> Clean Architecture (use cases and interface adapters)

## Current branch

`main` contains the Layered Architecture example:

- Endpoint: `POST /layered/todos`
- Endpoint: `GET /layered/todos`
- Package root: `com.awwad.example.architecture.layered`

## Canonical structure for each architecture branch

Use this package layout under `com.awwad.example.architecture.<pattern>`:

```text
domain/
  entity/
  repository/

application/
  usecase/

infrastructure/
  database/
  security/

presentation/
  controller/
```

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
